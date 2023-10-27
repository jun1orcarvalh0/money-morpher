package com.ada.moneymorpher.client;

import java.math.BigDecimal;

public record Result(int H, String error_message, BigDecimal amount) {
}
