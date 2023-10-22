package com.ada.moneymorpher.Transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@ToString
public class TransactionRequest {

    @NotBlank
    private String description;
    private BigDecimal BRLValue;
    @Pattern(regexp = "CASHIN|CASHOUT")
    private TransactionTypeEnum transactionType;
}
