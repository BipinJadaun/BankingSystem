package com.iongroup.userservice;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.bankingservice.BankingServiceManager;
import com.iongroup.bankingservice.BankingSystemInterface;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidDateException;
import com.iongroup.transactionservice.model.Transaction;

public class UserServiceManager implements UserInterface{

	private final BankingSystemInterface bankingService;

	public UserServiceManager() {
		this.bankingService = new BankingServiceManager();
	}

	@Override
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException {
		return bankingService.createAccount(name, openingBalance);
	}

	@Override
	public void closeAccount(Long accountNumber) throws AccountNotExistException {
		bankingService.closeAccount(accountNumber);
	}

	@Override
	public void deposit(Long accountNumber, double amount) throws AccountNotExistException {
		bankingService.deposit(accountNumber, amount);		
	}

	@Override
	public void withdraw(Long accountNumber, double amount)	throws AccountNotExistException, InsufficientBalanceException {
		bankingService.withdraw(accountNumber, amount);		
	}

	@Override
	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {
		bankingService.transfer(fromAccountNumber, toAccountNumber, amount);		
	}

	@Override
	public double getBalance(Long accountNumber) throws AccountNotExistException {
		return bankingService.getBalance(accountNumber);	
	}

	@Override
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException {
		return bankingService.getLastTenTrasactions(accountNumber);
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate) throws AccountNotExistException, InvalidDateException {
		return bankingService.getTrasactionsByTimeIntarval(accountNumber, fromDate, toDate);
	}
}
