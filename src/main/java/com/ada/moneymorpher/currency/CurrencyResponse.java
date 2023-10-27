package com.ada.moneymorpher.currency;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyResponse {

    private BigDecimal eurValue;
    private BigDecimal usdValue;
    private LocalDateTime createdAt;

}
