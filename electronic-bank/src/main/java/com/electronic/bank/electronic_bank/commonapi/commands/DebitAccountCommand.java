package com.electronic.bank.electronic_bank.commonapi.commands;

import lombok.Getter;

public class DebitAccountCommand extends BaseCommand<String>{
  @Getter
  private String currency;

  @Getter
  private double amount;

  public DebitAccountCommand(String id, String currency, double amount){
    super(id);
    this.currency = currency;
    this.amount = amount;
  }
}