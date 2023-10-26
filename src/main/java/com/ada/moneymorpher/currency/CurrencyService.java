package com.ada.moneymorpher.currency;

import com.ada.moneymorpher.client.CurrencyExchangeRestRepository;
import com.ada.moneymorpher.client.Result;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository repository;
    private final ModelMapper modelMapper;

    private CurrencyDto convertDto(Currency currency) {
        return this.modelMapper.map(currency, CurrencyDto.class);
    }

    public CurrencyDto create(CurrencyRequest request) {
        Currency currency = new Currency();
        currency.setEurValue(request.getEurValue());
        currency.setUsdValue(request.getUsdValue());
        currency.setCreatedAt(LocalDateTime.now());
//
        final var saved = this.repository.save(currency);
        return this.convertDto(saved);
    }

    public List<CurrencyDto> listCurrencies() {
        List<Currency> currencyList = this.repository.findAll();

        return currencyList.stream()
                .map(this::convertDto).toList();
    }
}