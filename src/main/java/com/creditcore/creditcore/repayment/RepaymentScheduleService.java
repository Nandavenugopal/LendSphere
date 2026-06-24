package com.creditcore.creditcore.repayment;

import com.creditcore.creditcore.loan.LoanApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentRepository repository;
    private final EmiCalculator emiCalculator;

    public void generateSchedule(LoanApplication loan) {

        BigDecimal emi = emiCalculator.calculateMonthlyEmi(
                loan.getRequestedAmount(),
                loan.getProduct().getInterestRate(),
                loan.getTenureMonths()
        );

        BigDecimal outstanding = loan.getRequestedAmount();

        BigDecimal monthlyRate = loan.getProduct().getInterestRate()
                .divide(new BigDecimal("12"), 10, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP);

        for (int i = 1; i <= loan.getTenureMonths(); i++) {

            BigDecimal interest = outstanding.multiply(monthlyRate)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            BigDecimal principal = emi.subtract(interest)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            outstanding = outstanding.subtract(principal);

            RepaymentSchedule row = new RepaymentSchedule();
            row.setLoanId(loan.getId());
            row.setInstallmentNumber(i);
            row.setDueDate(LocalDate.now().plusMonths(i));
            row.setPrincipalDue(principal);
            row.setInterestDue(interest);
            row.setStatus(InstallmentStatus.PENDING);

            repository.save(row);
        }
    }
}