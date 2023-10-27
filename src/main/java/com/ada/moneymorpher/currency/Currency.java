package com.ada.moneymorpher.currency;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "eur_value", precision = 16, scale = 4)
    private BigDecimal eurValue;
    @Column(name = "usd_value", precision = 16, scale = 4)
    private BigDecimal usdValue;
    private LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    public void pricePrecisionConvertion() {
        // convert your bigdecimal scale to 2 here
        this.eurValue.setScale(2, RoundingMode.HALF_UP);
        this.usdValue.setScale(2, RoundingMode.HALF_UP);
    }
}