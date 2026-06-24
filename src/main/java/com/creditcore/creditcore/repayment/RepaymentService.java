package com.creditcore.creditcore.repayment;

import com.creditcore.creditcore.common.IdempotencyRepository;
import com.creditcore.creditcore.ledger.LedgerService;
import com.creditcore.creditcore.loan.LoanApplicationRepository;
import com.creditcore.creditcore.loan.LoanApplicationService;
import com.creditcore.creditcore.loan.LoanApplicationService;
import com.creditcore.creditcore.loan.LoanApplication;
import com.creditcore.creditcore.loan.LoanApplicationRepository;
import com.creditcore.creditcore.loan.LoanStatus;
import com.creditcore.creditcore.common.IdempotencyRecord;
import com.creditcore.creditcore.common.IdempotencyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepaymentService {

    private final RepaymentRepository repository;
    private final LedgerService ledgerService;
    private final LoanApplicationService loanApplicationService;
    private final LoanApplicationRepository loanRepository;
    private final IdempotencyRepository idempotencyRepository;

    public void processPayment(Long loanId,
            BigDecimal paymentAmount,
            String idempotencyKey) {

        if (idempotencyRepository.findByKey(idempotencyKey).isPresent()) {
            throw new RuntimeException("Duplicate request detected. Payment already processed.");
        }

        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() == LoanStatus.CLOSED) {
            throw new RuntimeException("Loan is already CLOSED. No further payments allowed.");
        }

        List<RepaymentSchedule> schedules = repository.findByLoanIdOrderByInstallmentNumber(loanId);

        BigDecimal remaining = paymentAmount;

        for (RepaymentSchedule row : schedules) {

            if (remaining.compareTo(BigDecimal.ZERO) <= 0)
                break;
            if (row.getStatus() == InstallmentStatus.PAID)
                continue;

            BigDecimal interestDue = row.getInterestDue();
            BigDecimal principalDue = row.getPrincipalDue();

            // 1️⃣ Clear Interest
            BigDecimal interestPaid = remaining.min(interestDue);
            interestDue = interestDue.subtract(interestPaid);
            remaining = remaining.subtract(interestPaid);

            // 2️⃣ Clear Principal
            BigDecimal principalPaid = remaining.min(principalDue);
            principalDue = principalDue.subtract(principalPaid);
            remaining = remaining.subtract(principalPaid);

            row.setInterestDue(interestDue.setScale(2, RoundingMode.HALF_UP));
            row.setPrincipalDue(principalDue.setScale(2, RoundingMode.HALF_UP));

            if (interestDue.compareTo(BigDecimal.ZERO) == 0 &&
                    principalDue.compareTo(BigDecimal.ZERO) == 0) {
                row.setStatus(InstallmentStatus.PAID);
            } else {
                row.setStatus(InstallmentStatus.PARTIAL);
            }

            repository.save(row);
        }

        ledgerService.recordRepayment(loanId, paymentAmount);
        if (isLoanFullyPaid(loanId)) {
            loanApplicationService.closeIfFullyPaid(loanId);
        }
        IdempotencyRecord record = new IdempotencyRecord();
        record.setKey(idempotencyKey);
        record.setDescription("Repayment for loan " + loanId);
        idempotencyRepository.save(record);
    }

    private boolean isLoanFullyPaid(Long loanId) {
        return repository.findByLoanIdOrderByInstallmentNumber(loanId)
                .stream()
                .allMatch(r -> r.getStatus() == InstallmentStatus.PAID);
    }
}