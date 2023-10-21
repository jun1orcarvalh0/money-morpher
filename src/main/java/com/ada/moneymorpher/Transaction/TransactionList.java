package com.ada.moneymorpher.Transaction;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionList {
    UUID uuid;
    BigDecimal currencyValue;
    TransactionTypeEnum transactionType;
}
