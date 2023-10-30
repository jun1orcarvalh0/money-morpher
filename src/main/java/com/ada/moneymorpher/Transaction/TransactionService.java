package com.ada.moneymorpher.Transaction;


import com.ada.moneymorpher.currency.Currency;
import com.ada.moneymorpher.currency.CurrencyRepository;
import com.ada.moneymorpher.exceptions.ForbiddenException;
import com.ada.moneymorpher.exceptions.NotFoundException;
import com.ada.moneymorpher.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final CurrencyRepository currencyRepository;
    private final ProfileService profileService;
    private final ModelMapper modelMapper;

    private TransactionDto convertDto(Transaction transaction){
        return this.modelMapper.map(transaction, TransactionDto.class);
    }

    private TransactionList convertList(Transaction transaction, CurrencyTypeEnum currency){
        TransactionList converted = new TransactionList();

        converted.setUuid(transaction.getUuid());
        converted.setTransactionType(transaction.getTransactionType());


        if (currency.equals(CurrencyTypeEnum.USD)) {
            converted.setCurrencyValue(transaction.getUSDValue());
        } else if (currency.equals(CurrencyTypeEnum.EUR)) {
            converted.setCurrencyValue(transaction.getEURValue());
        } else {
            converted.setCurrencyValue(transaction.getBRLValue());
        }
        return converted;
    }


    public TransactionDto create(TransactionRequest request, String username){
        Transaction transaction = new Transaction();
        transaction.setUuid(UUID.randomUUID());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setBRLValue(request.getBRLValue());
        transaction.setCreatedAt(LocalDateTime.now());

        Currency exchange = this.currencyRepository.findFirstByOrderByCreatedAtDesc()
                .orElseThrow(() -> new NotFoundException("Exchange rates not found"));

        BigDecimal EURValue = request.getBRLValue().multiply(exchange.getEurValue());
        BigDecimal USDValue = request.getBRLValue().multiply(exchange.getUsdValue());

        transaction.setEURValue(EURValue);
        transaction.setUSDValue(USDValue);

        final var profile = this.profileService.getByUsernameEntity(username);
        transaction.setProfile(profile);

        final var saved = this.repository.save(transaction);
        return this.convertDto(saved);
    }

    public List<TransactionList> listAllTransactions(CurrencyTypeEnum currency) {
        List<Transaction> transactionList = this.repository.findAllByOrderByCreatedAtDesc();

        return transactionList.stream()
                .map((transaction -> convertList(transaction,currency)))
                .toList();
    }

    public List<TransactionList> listTransactions(CurrencyTypeEnum currency, String username) {
        List<Transaction> transactionList = this.repository.findByProfileUsernameOrdered(username);

        return transactionList.stream()
                .map((transaction -> convertList(transaction,currency)))
                .toList();
    }

    public BigDecimal listBalance(CurrencyTypeEnum currency, String username){
        BigDecimal balance;

        if(currency.equals(CurrencyTypeEnum.USD)){
            balance = this.repository.getUSDBalance(username);
        } else if (currency.equals(CurrencyTypeEnum.EUR)){
            balance = this.repository.getEURBalance(username);
        } else {
            balance = this.repository.getBRLBalance(username);
        }

        if(balance == null){
            balance = new BigDecimal(0);
        }
        return balance;
    }

    public TransactionDto listDetails (UUID uuid, String username){
        Transaction transaction = this.repository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));

        String transactionUsername = transaction.getProfile().getUsername();

        if(!transactionUsername.equalsIgnoreCase(username)){
            throw new ForbiddenException("Transaction not allowed to user");
        }

        return this.convertDto(transaction);
    }

    public TransactionDto update(UUID uuid, String description, String username){
        Transaction transaction = this.repository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));

        String transactionUsername = transaction.getProfile().getUsername();

        if(!transactionUsername.equalsIgnoreCase(username)){
            throw new ForbiddenException("Transaction not allowed to user");
        }

        transaction.setDescription(description);
        Transaction updated = this.repository.save(transaction);
        return this.convertDto(updated);
    }

    public void delete(UUID uuid, String username){
        Transaction transaction = this.repository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));

        String transactionUsername = transaction.getProfile().getUsername();

        if(!transactionUsername.equalsIgnoreCase(username)){
            throw new ForbiddenException("Transaction not allowed to user");
        }
        this.repository.delete(transaction);
    }
}
