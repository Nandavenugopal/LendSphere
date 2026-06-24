package com.creditcore.creditcore.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.creditcore.creditcore.ledger.LedgerService;
import com.creditcore.creditcore.repayment.RepaymentScheduleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final RepaymentScheduleService repaymentScheduleService;
    private final LedgerService ledgerService;
    private final LoanApplicationRepository repository;
    private final LoanProductRepository productRepository;
    private final CreditScoreEngine creditScoreEngine;

    public LoanApplication apply(LoanApplication application, Long productId) {

        LoanProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Invalid loan product"));

        validate(application, product);

        application.setProduct(product);
        application.setCreditScore(
                creditScoreEngine.calculateScore(
                        application.getMonthlyIncome(),
                        application.getRequestedAmount(),
                        application.getTenureMonths()));

        application.setStatus(LoanStatus.APPLIED);

        return repository.save(application);
    }

    public List<LoanApplication> getAll() {
        return repository.findAll();
    }

    private void validate(LoanApplication app, LoanProduct product) {

        if (app.getRequestedAmount().compareTo(product.getMinAmount()) < 0 ||
                app.getRequestedAmount().compareTo(product.getMaxAmount()) > 0) {
            throw new RuntimeException("Requested amount outside allowed range");
        }

        if (app.getTenureMonths() < product.getMinTenureMonths() ||
                app.getTenureMonths() > product.getMaxTenureMonths()) {
            throw new RuntimeException("Tenure outside allowed range");
        }
    }

    public LoanApplication approve(Long applicationId) {

        LoanApplication app = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (app.getStatus() != LoanStatus.APPLIED) {
            throw new RuntimeException("Only APPLIED loans can be approved");
        }

        // Simple approval rule: credit score >= 600
        if (app.getCreditScore() < 600) {
            throw new RuntimeException("Credit score too low for approval");
        }

        app.setStatus(LoanStatus.APPROVED);
        return repository.save(app);
    }

    public LoanApplication reject(Long applicationId) {

        LoanApplication app = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (app.getStatus() != LoanStatus.APPLIED) {
            throw new RuntimeException("Only APPLIED loans can be rejected");
        }

        app.setStatus(LoanStatus.REJECTED);
        return repository.save(app);
    }

    public LoanApplication disburse(Long applicationId) {

        LoanApplication app = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (app.getStatus() != LoanStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED loans can be disbursed");
        }

        ledgerService.recordDisbursement(
                app.getId(),
                app.getRequestedAmount());

        app.setStatus(LoanStatus.ACTIVE);
        LoanApplication saved = repository.save(app);

        // Generate repayment schedule
        repaymentScheduleService.generateSchedule(saved);

        return saved;
    }

    public void closeIfFullyPaid(Long loanId) {

        LoanApplication loan = repository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() == LoanStatus.ACTIVE) {
            loan.setStatus(LoanStatus.CLOSED);
            repository.save(loan);
            System.out.println("🔒 Loan " + loanId + " CLOSED.");
        }
    }
}