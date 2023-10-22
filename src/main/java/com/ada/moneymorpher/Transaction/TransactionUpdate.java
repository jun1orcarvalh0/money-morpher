package com.ada.moneymorpher.Transaction;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TransactionUpdate {

    @NotBlank
    private String description;
}
