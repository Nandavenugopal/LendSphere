package com.creditcore.creditcore.loan;

import com.creditcore.creditcore.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "loan_applications")
public class LoanApplication extends BaseEntity {

    @ManyToOne(optional = false)
    private LoanProduct product;

    @Column(nullable = false)
    private String applicantName;

    @Column(nullable = false)
    private BigDecimal monthlyIncome;

    @Column(nullable = false)
    private BigDecimal requestedAmount;

    @Column(nullable = false)
    private Integer tenureMonths;

    private Integer creditScore;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}