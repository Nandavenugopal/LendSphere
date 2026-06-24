package com.creditcore.creditcore.repayment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RepaymentRequest {
    private Long loanId;
    private BigDecimal amount;
    private String idempotencyKey;
}