package com.electronic.bank.electronic_bank.commonapi.exceptions;

public class NegativeInitialBalanceException extends RuntimeException{
    public NegativeInitialBalanceException(String message){
      super(message);
    }
}