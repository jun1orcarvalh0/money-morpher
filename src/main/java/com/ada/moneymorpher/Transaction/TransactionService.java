package com.ada.moneymorpher.Transaction;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final ModelMapper modelMapper;

    private TransactionDto convertDto(Transaction transaction){
        return this.modelMapper.map(transaction, TransactionDto.class);
    }


    public TransactionDto cadastrar(TransactionRequest request){
        Transaction transaction = new Transaction();
        transaction.setUuid(UUID.randomUUID());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setBRLValue(request.getBRLValue());
        transaction.setCreatedAt(LocalDateTime.now());

        final var saved = this.repository.save(transaction);
        return this.convertDto(saved);
    }
}
