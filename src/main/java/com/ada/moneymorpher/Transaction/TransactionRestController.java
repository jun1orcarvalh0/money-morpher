package com.ada.moneymorpher.Transaction;


import com.ada.moneymorpher.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

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
    @PreAuthorize("hasRole(T(com/ada/moneymorpher/profile/Role).CLIENT.name())")
    public TransactionResponse create(@RequestBody TransactionRequest request, Principal principal){
        TransactionDto response = this.service.create(request, principal.getName());
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

    @GetMapping("/{uuid}")
    public TransactionResponse listDetails(@PathVariable UUID uuid){
        return this.service.listDetails(uuid)
                .map(this::convertReponse)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));
    }

    @PutMapping("/{uuid}")
    public TransactionResponse update(@PathVariable UUID uuid, @RequestBody TransactionUpdate request){
        TransactionDto updated = this.service.update(uuid, request.getDescription());
        return this.convertReponse(updated);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        this.service.delete(uuid);
    }
}
