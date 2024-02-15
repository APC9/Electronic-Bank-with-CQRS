package com.electronic.bank.electronic_bank.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronic.bank.electronic_bank.query.entities.AccountTransaction;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
    AccountTransaction findTop1ByAccountIdOrderByTimestampDesc(String accountId);
}