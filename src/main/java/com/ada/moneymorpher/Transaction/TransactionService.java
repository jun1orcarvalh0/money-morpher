package com.ada.moneymorpher.Transaction;


import com.ada.moneymorpher.currency.Currency;
import com.ada.moneymorpher.currency.CurrencyRepository;
import com.ada.moneymorpher.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final CurrencyRepository currencyRepository;
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


    public TransactionDto create(TransactionRequest request){
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

        final var saved = this.repository.save(transaction);
        return this.convertDto(saved);
    }

    public List<TransactionList> listTransactions(CurrencyTypeEnum currency) {
        List<Transaction> transactionList = this.repository.findAllByOrderByCreatedAtDesc();

        return transactionList.stream()
                .map((transaction -> convertList(transaction,currency)))
                .toList();
    }

    public BigDecimal listBalance(CurrencyTypeEnum currency){
        BigDecimal balance;

        if(currency.equals(CurrencyTypeEnum.USD)){
            balance = this.repository.getUSDBalance();
        } else if (currency.equals(CurrencyTypeEnum.EUR)){
            balance = this.repository.getEURBalance();
        } else {
            balance = this.repository.getBRLBalance();
        }

        if(balance == null){
            balance = new BigDecimal(0);
        }
        return balance;
    }

    public Optional<TransactionDto> listDetails (UUID uuid){
        return this.repository.findByUuid(uuid).map(this::convertDto);
    }

    public TransactionDto update(UUID uuid, String description){
        Transaction transaction = this.repository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));
        transaction.setDescription(description);
        Transaction updated = this.repository.save(transaction);
        return this.convertDto(updated);
    }

    public void delete(UUID uuid){
        Transaction transaction = this.repository.findByUuid(uuid)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));
        this.repository.delete(transaction);
    }
}
