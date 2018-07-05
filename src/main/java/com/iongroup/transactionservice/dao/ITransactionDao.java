package com.iongroup.transactionservice.dao;

import java.util.Date;
import java.util.List;

import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.model.Transaction;
import com.iongroup.transactionservice.model.TransactionType;

public interface ITransactionDao {

	public void deposit(Long accountNumber, double amount);

	public void withdraw(Long accountNumber, double amount) ;

	public double getBalance(Long accountNumber);

	public void addTransection(Long accountNumber, TransactionType traxType, double amount);

	public void removeTransactions(Long accountNumber);
	
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException;
	
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, Date fromDate, Date toDate) throws AccountNotExistException;
}
