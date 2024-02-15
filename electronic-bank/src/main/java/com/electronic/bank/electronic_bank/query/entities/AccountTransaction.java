package com.electronic.bank.electronic_bank.query.entities;

import java.time.Instant;

import com.electronic.bank.electronic_bank.commonapi.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountTransaction {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant timestamp;
  private double amount;

  @Enumerated
  private TransactionType transactionType;

  @ManyToOne
  @JsonProperty( access = JsonProperty.Access.WRITE_ONLY)
  private Account account;

  
}