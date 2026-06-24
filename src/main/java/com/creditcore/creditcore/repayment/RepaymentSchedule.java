package com.creditcore.creditcore.repayment;

import com.creditcore.creditcore.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "repayment_schedule")
public class RepaymentSchedule extends BaseEntity {

    @Column(nullable = false)
    private Long loanId;

    private Integer installmentNumber;

    private LocalDate dueDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal principalDue;

    @Column(precision = 15, scale = 2)
    private BigDecimal interestDue;

    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;
}