package com.creditcore.creditcore.repayment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EmiCalculator {

    private static final int SCALE = 10;

    public BigDecimal calculateMonthlyEmi(
            BigDecimal principal,
            BigDecimal annualRate,
            int tenureMonths
    ) {

        BigDecimal monthlyRate = annualRate
                .divide(new BigDecimal("12"), SCALE, RoundingMode.HALF_UP)
                .divide(new BigDecimal("100"), SCALE, RoundingMode.HALF_UP);

        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(new BigDecimal(tenureMonths), 2, RoundingMode.HALF_UP);
        }

        BigDecimal onePlusRPowerN =
                monthlyRate.add(BigDecimal.ONE).pow(tenureMonths);

        BigDecimal numerator =
                principal.multiply(monthlyRate).multiply(onePlusRPowerN);

        BigDecimal denominator =
                onePlusRPowerN.subtract(BigDecimal.ONE);

        return numerator
                .divide(denominator, 2, RoundingMode.HALF_UP);
    }
}