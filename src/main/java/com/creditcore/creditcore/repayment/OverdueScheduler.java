package com.creditcore.creditcore.repayment;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.creditcore.creditcore.ledger.LedgerService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import com.creditcore.creditcore.ledger.LedgerService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OverdueScheduler {

        private final RepaymentRepository repository;
        private final LedgerService ledgerService;

        // Runs every 60 seconds (for demo)
        @Scheduled(fixedRate = 60000)
        public void markOverdueInstallments() {

                List<RepaymentSchedule> overdue = repository.findByDueDateBeforeAndStatusIn(
                                LocalDate.now(),
                                List.of(
                                                InstallmentStatus.PENDING,
                                                InstallmentStatus.PARTIAL,
                                                InstallmentStatus.OVERDUE));

                if (overdue.isEmpty())
                        return;

                overdue.forEach(row -> {

                        // Simple daily penalty: 1% of remaining principal
                        BigDecimal penalty = row.getPrincipalDue()
                                        .multiply(new BigDecimal("0.01"))
                                        .setScale(2, RoundingMode.HALF_UP);

                        // Add penalty into interest bucket
                        row.setInterestDue(
                                        row.getInterestDue().add(penalty));

                        row.setStatus(InstallmentStatus.OVERDUE);
                        repository.save(row);

                        ledgerService.recordPenalty(
                                        row.getLoanId(),
                                        penalty);
                });

                System.out.println("💸 Penalty accrued for "
                                + overdue.size() + " overdue installments");
        }
}