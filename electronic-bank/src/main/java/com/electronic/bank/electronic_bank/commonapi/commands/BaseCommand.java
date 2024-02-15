package com.electronic.bank.electronic_bank.commonapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

public class BaseCommand<T> {
 
  @TargetAggregateIdentifier
  @Getter
  private T id;

  public BaseCommand(T id) {
    this.id = id;
  }
}