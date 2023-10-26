package com.ada.moneymorpher.currency;

import com.ada.moneymorpher.client.CurrencyExchangeRestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CurrencyPostOnSetup {

    private final CurrencyExchangeRestRepository externalService;
    private final CurrencyRepository repository;

    @PostConstruct
    public void setupPost() throws JsonProcessingException {
        String json = externalService.getExchangesRate();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        JsonNode brlEurNode = rootNode.get("BRLEUR");
        JsonNode brlUsdNode = rootNode.get("BRLUSD");

        if (brlEurNode != null && brlUsdNode != null) {
            BigDecimal highBrlEur = new BigDecimal(brlEurNode.get("high").asText());
            BigDecimal highBrlUsd = new BigDecimal(brlUsdNode.get("high").asText());

            Currency currency = new Currency();
            currency.setEurValue(highBrlEur);
            currency.setUsdValue(highBrlUsd);
            currency.setCreatedAt(LocalDateTime.now());

            this.repository.save(currency);
        }
    }
}