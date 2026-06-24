package com.creditcore.creditcore.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanProductService {

    private final LoanProductRepository repository;

    public LoanProduct create(LoanProduct product) {

        validate(product);
        return repository.save(product);
    }

    public List<LoanProduct> getAll() {
        return repository.findAll();
    }

    private void validate(LoanProduct product) {

        if (product.getMinTenureMonths() >= product.getMaxTenureMonths()) {
            throw new RuntimeException("Min tenure must be less than max tenure");
        }

        if (product.getMinAmount().compareTo(product.getMaxAmount()) >= 0) {
            throw new RuntimeException("Min amount must be less than max amount");
        }

        if (product.getInterestRate().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Interest rate must be positive");
        }

        if (product.getPenaltyRate().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Penalty rate cannot be negative");
        }
    }
}