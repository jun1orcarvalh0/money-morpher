package com.ada.moneymorpher.Transaction;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService service;
    private final ModelMapper modelMapper;

    private TransactionResponse convertReponse(TransactionDto transaction){
        return this.modelMapper.map(transaction, TransactionResponse.class);
    }

    @PostMapping
    public TransactionResponse create(@RequestBody TransactionRequest request, Principal principal){
        TransactionDto response = this.service.create(request, principal.getName());
        return this.convertReponse(response);
    }

    @GetMapping
    public List<TransactionList> listTransactions (@RequestParam CurrencyTypeEnum currency, Principal principal){
        return this.service.listTransactions(currency, principal.getName());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole(T(com.ada.moneymorpher.profile.Role).ADMIN.name())")
    public List<TransactionList> listAllTransactions (@RequestParam CurrencyTypeEnum currency){
        return this.service.listAllTransactions(currency);
    }


    @GetMapping("/balance")
    public BigDecimal listBalance(@RequestParam CurrencyTypeEnum currency, Principal principal){
        return this.service.listBalance(currency, principal.getName());
    }

    @GetMapping("/{uuid}")
    public TransactionResponse listDetails(@PathVariable UUID uuid, Principal principal){
        TransactionDto transaction = this.service.listDetails(uuid, principal.getName());

        return this.convertReponse(transaction);
    }

    @PutMapping("/{uuid}")
    public TransactionResponse update(@PathVariable UUID uuid, @RequestBody TransactionUpdate request, Principal principal){
        TransactionDto updated = this.service.update(uuid, request.getDescription(), principal.getName());
        return this.convertReponse(updated);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid, Principal principal){
        this.service.delete(uuid, principal.getName());
    }
}
