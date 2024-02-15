package com.electronic.bank.electronic_bank.query.dto;

import java.time.Instant;

import com.electronic.bank.electronic_bank.commonapi.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountWatchEvent {
    
  private Instant instant;
  private String accountId;
  private double currentbalance;
  private TransactionType type;
  private double transactionAmount;
}