package com.ada.moneymorpher.currency;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    public CurrencyResponse create(CurrencyRequest request){
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
