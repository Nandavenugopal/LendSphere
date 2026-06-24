package com.creditcore.creditcore.loan;

import com.creditcore.creditcore.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "loan_products")
public class LoanProduct extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    // Annual interest rate (example: 12.5%)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    // Penalty interest per day (%)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal penaltyRate;

    @Column(nullable = false)
    private Integer minTenureMonths;

    @Column(nullable = false)
    private Integer maxTenureMonths;

    @Column(nullable = false)
    private BigDecimal minAmount;

    @Column(nullable = false)
    private BigDecimal maxAmount;

    @Enumerated(EnumType.STRING)
    private InterestType interestType;
}