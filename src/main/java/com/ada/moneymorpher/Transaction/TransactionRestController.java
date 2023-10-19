package com.ada.moneymorpher.Transaction;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        TransactionDto response = this.service.cadastrar(request);
        return this.convertReponse(response);
    }
}
