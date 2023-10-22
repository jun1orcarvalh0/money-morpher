package com.ada.moneymorpher.Transaction;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TransactionResponse {
    private UUID uuid;
    private String description;
    private BigDecimal BRLValue;
    private BigDecimal USDValue;
    private BigDecimal EURValue;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;
}
