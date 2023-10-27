package com.ada.moneymorpher.currency;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CurrencyRequest {

    private BigDecimal eurValue;
    private BigDecimal usdValue;
}
