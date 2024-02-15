package com.electronic.bank.electronic_bank.commonapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitAccountRequestDto {
  private String accountId;
  private String currency;
  private double amount;
}