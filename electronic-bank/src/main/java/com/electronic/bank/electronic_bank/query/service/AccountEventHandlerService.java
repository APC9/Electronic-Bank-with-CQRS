package com.electronic.bank.electronic_bank.query.service;

import java.util.List;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electronic.bank.electronic_bank.commonapi.enums.TransactionType;
import com.electronic.bank.electronic_bank.commonapi.events.AccountCreatedEvent;
import com.electronic.bank.electronic_bank.commonapi.events.AccountCreditEvent;
import com.electronic.bank.electronic_bank.commonapi.events.AccountDebitEvent;
import com.electronic.bank.electronic_bank.query.dto.AccountWatchEvent;
import com.electronic.bank.electronic_bank.query.entities.Account;
import com.electronic.bank.electronic_bank.query.entities.AccountTransaction;
import com.electronic.bank.electronic_bank.query.queries.GetAccountBalanceStream;
import com.electronic.bank.electronic_bank.query.queries.GetAllAccountById;
import com.electronic.bank.electronic_bank.query.queries.GetAllAccounts;
import com.electronic.bank.electronic_bank.query.repository.AccountRepository;
import com.electronic.bank.electronic_bank.query.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class AccountEventHandlerService {
    
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private QueryUpdateEmitter queryUpdateEmitter;

  @EventHandler
  public void on(AccountCreatedEvent accountCreatedEvent, EventMessage<AccountCreatedEvent> eventMessage) {
    log.info("AccountCreatedEvent recived");

    Account account = new Account();
      account.setId(accountCreatedEvent.getId());
      account.setBalance(accountCreatedEvent.getBalance());
      account.setStatus(accountCreatedEvent.getStatus());
      account.setCurrency(accountCreatedEvent.getCurrency());
      account.setCreatedAt(eventMessage.getTimestamp().toString());

    accountRepository.save(account);
  }

  @EventHandler
  public void on(AccountCreditEvent accountCreditEvent, EventMessage<AccountCreditEvent> eventMessage) {
    log.info("AccountCreatedEvent recived");

    Account account = accountRepository.findById(accountCreditEvent.getId()).get();
    AccountTransaction accountTransaction = AccountTransaction.builder()
      .account(account)
      .amount(accountCreditEvent.getAmount())
      .transactionType(TransactionType.CREDIT)
      .timestamp(eventMessage.getTimestamp())
      .build();
    
    transactionRepository.save(accountTransaction);
    account.setBalance(account.getBalance() + accountCreditEvent.getAmount());
    accountRepository.save(account);

    AccountWatchEvent accountWatchEvent = new AccountWatchEvent(
      accountTransaction.getTimestamp(), 
      account.getId(), 
      account.getBalance(),
      accountTransaction.getTransactionType(),
      accountTransaction.getAmount()
    );

    queryUpdateEmitter.emit(
      GetAccountBalanceStream.class,
      (query)-> (query.getAccountId().equals(account.getId())), 
      accountWatchEvent
    );  
  }

  @EventHandler
  public void on(AccountDebitEvent accountDebitEvent, EventMessage<AccountDebitEvent> eventMessage) {
    log.info("AccountDebitEvent recived");

    Account account = accountRepository.findById(accountDebitEvent.getId()).get();
    AccountTransaction accountTransaction = AccountTransaction.builder()
      .account(account)
      .amount(accountDebitEvent.getAmount())
      .transactionType(TransactionType.DEBIT)
      .timestamp(eventMessage.getTimestamp())
      .build();
    
    transactionRepository.save(accountTransaction);
    account.setBalance(account.getBalance() - accountDebitEvent.getAmount());
    accountRepository.save(account);

    AccountWatchEvent accountWatchEvent = new AccountWatchEvent(
      accountTransaction.getTimestamp(), 
      account.getId(), 
      account.getBalance(),
      accountTransaction.getTransactionType(),
      accountTransaction.getAmount()
    );

    queryUpdateEmitter.emit(
      GetAccountBalanceStream.class,
      (query)-> (query.getAccountId().equals(account.getId())), 
      accountWatchEvent
    );  
  }

  @QueryHandler
  public List<Account> on(GetAllAccounts query){
    return accountRepository.findAll();
  }

  @QueryHandler
  public Account on(GetAllAccountById query){
    return accountRepository.findById(query.getAccountId()).get();
  }

  @QueryHandler
  public AccountWatchEvent on(GetAccountBalanceStream query){
    Account account = accountRepository.findById(query.getAccountId()).get();
    AccountTransaction accountTransaction = transactionRepository.findTop1ByAccountIdOrderByTimestampDesc(query.getAccountId());

    if(accountTransaction != null){
      return new AccountWatchEvent(
        accountTransaction.getTimestamp(),
        account.getId(),
        account.getBalance(),
        accountTransaction.getTransactionType(),
        accountTransaction.getAmount()
      );
    }
    return null;
  }
}