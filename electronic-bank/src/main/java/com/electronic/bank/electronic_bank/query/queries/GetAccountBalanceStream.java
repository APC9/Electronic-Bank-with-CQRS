package com.electronic.bank.electronic_bank.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountBalanceStream {
  private String accountId;    
}