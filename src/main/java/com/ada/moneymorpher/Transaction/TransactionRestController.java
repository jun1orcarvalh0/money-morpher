package com.ada.moneymorpher.Transaction;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService service;
    private final ModelMapper modelMapper;

    private TransactionResponse convertReponse(TransactionDto transaction){
        return this.modelMapper.map(transaction, TransactionResponse.class);
    }

    @PostMapping
    public TransactionResponse create(@RequestBody TransactionRequest request){
        TransactionDto response = this.service.create(request);
        return this.convertReponse(response);
    }

    @GetMapping(params = {"currency"})
    public List<TransactionList> listTransactions (@RequestParam CurrencyTypeEnum currency){
        return this.service.listTransactions(currency);
    }

    @GetMapping("/balance")
    public BigDecimal listBalance(@RequestParam CurrencyTypeEnum currency){
        return this.service.listBalance(currency);
    }
}
