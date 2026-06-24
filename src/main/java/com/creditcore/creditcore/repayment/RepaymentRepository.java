package com.creditcore.creditcore.repayment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RepaymentRepository extends JpaRepository<RepaymentSchedule, Long> {

    List<RepaymentSchedule> findByLoanIdOrderByInstallmentNumber(Long loanId);

    List<RepaymentSchedule> findByDueDateBeforeAndStatusIn(
            LocalDate date,
            List<InstallmentStatus> statuses
    );
}