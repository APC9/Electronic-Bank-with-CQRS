package com.electronic.bank.electronic_bank.query.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.bank.electronic_bank.query.entities.Account;
import com.electronic.bank.electronic_bank.query.queries.GetAllAccountById;
import com.electronic.bank.electronic_bank.query.queries.GetAllAccounts;

@RestController
@RequestMapping("/query/account")
public class AccountQueryController {

  @Autowired
  private QueryGateway queryGateway;

  @GetMapping("/list")
  public CompletableFuture<List<Account>> getAllAccounts(){
    return queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
  }

  @GetMapping("/byId/{id}")
  public CompletableFuture<Account> getAccountById(@PathVariable String id){
    return queryGateway.query(new GetAllAccountById(id), ResponseTypes.instanceOf(Account.class));
  }
    
}