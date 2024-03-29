package com.electronic.bank.electronic_bank.commonapi.events;

import com.electronic.bank.electronic_bank.commonapi.enums.AccountStatus;

import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String>{
  
  @Getter
  private String currency;

  @Getter
  private double balance;

  @Getter
  private AccountStatus status;

  public AccountCreatedEvent(String id, String currency, double balance, AccountStatus status){
    super(id);
    this.currency = currency;
    this.balance = balance;
    this.status = status;
  }
}