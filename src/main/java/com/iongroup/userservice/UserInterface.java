package com.iongroup.userservice;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidDateException;
import com.iongroup.transactionservice.model.Transaction;

public interface UserInterface{
	
	public Long createAccount(String name, double openingBalance) throws AccountAlreadyExistException ;
	
	public void closeAccount(Long accountNumber) throws AccountNotExistException;
	
	public void deposit(Long accountNumber, double amount) throws AccountNotExistException;
	
	public void withdraw(Long accountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException;
	
	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException;
	
	public double getBalance(Long accountNumber) throws AccountNotExistException;
	
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException;
	
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate) throws AccountNotExistException, InvalidDateException;


}
