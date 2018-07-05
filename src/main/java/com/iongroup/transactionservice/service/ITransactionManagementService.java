package com.iongroup.transactionservice.service;

import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;

public interface ITransactionManagementService {
	
	public void deposit(Long accountNumber, double amount) throws AccountNotExistException;
	public void withdraw(Long accountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException;
	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount);

}
