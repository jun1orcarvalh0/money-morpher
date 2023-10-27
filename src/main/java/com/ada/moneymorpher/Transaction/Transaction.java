package com.ada.moneymorpher.Transaction;

import com.ada.moneymorpher.profile.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String description;
    private BigDecimal BRLValue;
    private BigDecimal USDValue;
    private BigDecimal EURValue;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
