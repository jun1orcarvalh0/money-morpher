package com.ada.moneymorpher.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "currencyExchangeRest", url = "${dummy.url.exchanges}")
public interface CurrencyExchangeRestRepository {

    @GetMapping()
    String getExchangesRate();

}
