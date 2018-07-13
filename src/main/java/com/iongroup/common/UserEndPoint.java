package com.iongroup.common;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.accountservice.endpoint.AccountServiceEndPoint;
import com.iongroup.accountservice.endpoint.IAccountServiceEndPoint;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.endpoint.ITransactionServiceEndPoint;
import com.iongroup.transactionservice.endpoint.TransactionSerivceEndPoint;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidTimeIntarvalException;
import com.iongroup.transactionservice.model.Transaction;

public class UserEndPoint implements IAccountServiceEndPoint, ITransactionServiceEndPoint{

	IAccountServiceEndPoint accountService;
	ITransactionServiceEndPoint transactionService;

	public UserEndPoint() {
		this.accountService = new AccountServiceEndPoint();
		this.transactionService = new TransactionSerivceEndPoint();
	}

	@Override
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException {
		return accountService.createAccount(name, openingBalance);
	}

	@Override
	public void closeAccount(Long accountNumber) throws AccountNotExistException {
		accountService.closeAccount(accountNumber);
	}

	@Override
	public void deposit(Long accountNumber, double amount) throws AccountNotExistException {
		transactionService.deposit(accountNumber, amount);		
	}

	@Override
	public void withdraw(Long accountNumber, double amount)	throws AccountNotExistException, InsufficientBalanceException {
		transactionService.withdraw(accountNumber, amount);		
	}

	@Override
	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {
		transactionService.transfer(fromAccountNumber, toAccountNumber, amount);		
	}

	@Override
	public double getBalance(Long accountNumber) throws AccountNotExistException {
		return transactionService.getBalance(accountNumber);	
	}

	@Override
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException {
		return transactionService.getLatestTrasactions(accountNumber);
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate) throws AccountNotExistException, InvalidTimeIntarvalException {
		return transactionService.getTrasactionsByTimeIntarval(accountNumber, fromDate, toDate);
	}
}
