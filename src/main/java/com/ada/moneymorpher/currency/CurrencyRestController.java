package com.ada.moneymorpher.currency;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyRestController {

    private final CurrencyService service;
    private final ModelMapper modelMapper;
    
    @PostMapping
    @PreAuthorize("hasRole(T(com.ada.moneymorpher.profile.Role).ADMIN.name())")
    public CurrencyResponse create(@RequestBody CurrencyRequest request){
        System.out.println(request);
        CurrencyDto response = this.service.create(request);
        return this.convertResponse(response);
    }

    @GetMapping()
    public List<CurrencyDto> listCurrencies (){

        return this.service.listCurrencies();
    }

    private CurrencyResponse convertResponse(CurrencyDto currency) {
        return this.modelMapper.map(currency, CurrencyResponse.class);
    }
}
