package com.ada.moneymorpher.currency;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyDto {
    private Long id;
    private BigDecimal eurValue;
    private BigDecimal usdValue;
    private LocalDateTime createdAt;
}
