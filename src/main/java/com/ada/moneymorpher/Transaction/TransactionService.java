package com.ada.moneymorpher.Transaction;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
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

        final var saved = this.repository.save(transaction);
        return this.convertDto(saved);
    }

    public List<TransactionList> listTransactions(CurrencyTypeEnum currency) {
        List<Transaction> transactionList = this.repository.findAllByOrderByCreatedAtDesc();

        return transactionList.stream()
                .map((transaction -> convertList(transaction,currency)))
                .toList();
    }
}
