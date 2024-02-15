package com.electronic.bank.electronic_bank.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronic.bank.electronic_bank.query.entities.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    
}