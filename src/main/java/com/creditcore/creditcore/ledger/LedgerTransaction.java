package com.creditcore.creditcore.ledger;

import com.creditcore.creditcore.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ledger_transactions")
public class LedgerTransaction extends BaseEntity {

    @Column(nullable = false)
    private Long referenceId; // Loan ID

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    private String description;
}