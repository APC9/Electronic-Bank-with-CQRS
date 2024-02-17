package com.electronic.bank.electronic_bank.command.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.electronic.bank.electronic_bank.commonapi.commands.CreateAccountCommand;
import com.electronic.bank.electronic_bank.commonapi.commands.CreditAccountCommand;
import com.electronic.bank.electronic_bank.commonapi.commands.DebitAccountCommand;
import com.electronic.bank.electronic_bank.commonapi.enums.AccountStatus;
import com.electronic.bank.electronic_bank.commonapi.events.AccountCreatedEvent;
import com.electronic.bank.electronic_bank.commonapi.events.AccountCreditEvent;
import com.electronic.bank.electronic_bank.commonapi.events.AccountDebitEvent;
import com.electronic.bank.electronic_bank.commonapi.exceptions.NegativeInitialBalanceException;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@Aggregate
@Slf4j
public class AccountAggregate {

  @AggregateIdentifier
  private String accountId;

  private String currency;

  private double balance;

  private AccountStatus accountStatus;

  public AccountAggregate(){} // Constructor Obligatorio solicitado por Axonframework

  @CommandHandler
  public AccountAggregate(CreateAccountCommand createAccountCommand){
    log.info("CreateAccountCommand recived");

    if( createAccountCommand.getInitialBalance() < 0 ){
      throw new NegativeInitialBalanceException("Error, your balance is negative");
    }

    AggregateLifecycle.apply( new AccountCreatedEvent(
      createAccountCommand.getId(), 
      createAccountCommand.getCurrency(), 
      createAccountCommand.getInitialBalance(), 
      AccountStatus.CREATED)
    );
  }

  @EventSourcingHandler
  public void on( AccountCreatedEvent accountCreatedEvent ){
    log.info("Event AccountCreatedEvent");
  
    this.accountId = accountCreatedEvent.getId();
    this.accountStatus = accountCreatedEvent.getStatus();
    this.balance = accountCreatedEvent.getBalance();
    this.currency = accountCreatedEvent.getCurrency();
  }

  @CommandHandler
  public void handle( CreditAccountCommand creditAccountCommand){
    log.info("CreditAccountCommand recived");

    if( creditAccountCommand.getAmount() < 0 ){
      throw new NegativeInitialBalanceException("Error, your balance is negative");
    }

    AggregateLifecycle.apply(new AccountCreditEvent(
      creditAccountCommand.getId(), 
      creditAccountCommand.getCurrency(), 
      creditAccountCommand.getAmount())
    );
  }

  @EventSourcingHandler
  public void on( AccountCreditEvent accountCreditEvent) {
    log.info("Event AccountCreditEvent");
    this.balance += accountCreditEvent.getAmount(); 
  }

  @CommandHandler
  public void handle(DebitAccountCommand debitAccountCommand){
    log.info("DebitAccountCommand recived");

    if( debitAccountCommand.getAmount() < 0 ){
      throw new NegativeInitialBalanceException("Error, your balance is negative");
    }

    if( debitAccountCommand.getAmount() > this.balance ){
      throw new NegativeInitialBalanceException("Error, your balance is insufficient");
    }

    AggregateLifecycle.apply( new AccountDebitEvent(
      debitAccountCommand.getId(), 
      debitAccountCommand.getCurrency(), 
      debitAccountCommand.getAmount())
    );
  }

  @EventSourcingHandler
  public void on( AccountDebitEvent accountDebitEvent) {
    log.info("Event AccountCreditEvent");
    this.balance -= accountDebitEvent.getAmount(); 
  }

}