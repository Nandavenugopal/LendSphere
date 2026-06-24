package com.creditcore.creditcore.ledger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepository extends JpaRepository<LedgerTransaction, Long> {
}