package com.creditcore.creditcore.loan;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreditScoreEngine {

    public int calculateScore(
            BigDecimal monthlyIncome,
            BigDecimal requestedAmount,
            Integer tenureMonths
    ) {
        int score = 700; // base score

        // Debt burden heuristic
        BigDecimal monthlyBurden = requestedAmount
                .divide(new BigDecimal(tenureMonths), BigDecimal.ROUND_HALF_UP);

        if (monthlyBurden.compareTo(monthlyIncome.multiply(new BigDecimal("0.4"))) > 0) {
            score -= 150;
        }

        if (tenureMonths > 36) {
            score -= 50;
        }

        return Math.max(score, 300); // minimum floor
    }
}