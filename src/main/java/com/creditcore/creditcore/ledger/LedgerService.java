package com.creditcore.creditcore.ledger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository repository;

    public void recordDisbursement(Long loanId, BigDecimal amount) {

        LedgerTransaction tx = new LedgerTransaction();
        tx.setReferenceId(loanId);
        tx.setType(TransactionType.DISBURSEMENT);
        tx.setAmount(amount);
        tx.setDescription("Loan disbursed");

        repository.save(tx);
    }

    public void recordRepayment(Long loanId, BigDecimal amount) {

        LedgerTransaction tx = new LedgerTransaction();
        tx.setReferenceId(loanId);
        tx.setType(TransactionType.REPAYMENT);
        tx.setAmount(amount);
        tx.setDescription("Loan repayment");

        repository.save(tx);
    }

    public void recordPenalty(Long loanId, BigDecimal amount) {

        LedgerTransaction tx = new LedgerTransaction();
        tx.setReferenceId(loanId);
        tx.setType(TransactionType.PENALTY);
        tx.setAmount(amount);
        tx.setDescription("Late payment penalty");

        repository.save(tx);
    }
}