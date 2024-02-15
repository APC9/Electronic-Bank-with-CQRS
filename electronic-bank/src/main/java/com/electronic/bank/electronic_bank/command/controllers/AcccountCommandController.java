package com.electronic.bank.electronic_bank.command.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.bank.electronic_bank.commonapi.commands.CreateAccountCommand;
import com.electronic.bank.electronic_bank.commonapi.commands.CreditAccountCommand;
import com.electronic.bank.electronic_bank.commonapi.commands.DebitAccountCommand;
import com.electronic.bank.electronic_bank.commonapi.dto.CreateAccountRequestDto;
import com.electronic.bank.electronic_bank.commonapi.dto.CreditAccountRequestDto;
import com.electronic.bank.electronic_bank.commonapi.dto.DebitAccountRequestDto;

@RestController
@RequestMapping("/commands/account")
public class AcccountCommandController {
 
  @Autowired
  private CommandGateway commandGateway;

  @PostMapping("/create")
  public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDto createAccountRequestDto){
    return commandGateway.send(new CreateAccountCommand(
      UUID.randomUUID().toString(), 
      createAccountRequestDto.getCurrency(), 
      createAccountRequestDto.getInitialBalance())
    );
  }
 
  @PostMapping("/debit")
  public CompletableFuture<String> makeDebit(@RequestBody DebitAccountRequestDto debitAccountRequestDto){
    return commandGateway.send(new DebitAccountCommand(
      debitAccountRequestDto.getAccountId(), 
      debitAccountRequestDto.getCurrency(), 
      debitAccountRequestDto.getAmount())
    );
  }

  @PostMapping("/credit")
  public CompletableFuture<String> makeCredit(@RequestBody CreditAccountRequestDto creditAccountRequestDto){
    return commandGateway.send(new CreditAccountCommand(
      creditAccountRequestDto.getAccountId(), 
      creditAccountRequestDto.getCurrency(), 
      creditAccountRequestDto.getAmount())
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandler(Exception exception){
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}