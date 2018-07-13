package com.iongroup.transactionservice.endpoint;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidTimeIntarvalException;
import com.iongroup.transactionservice.model.Transaction;
import com.iongroup.transactionservice.service.ITransactionManagementService;
import com.iongroup.transactionservice.service.ITransactionRetrievalService;
import com.iongroup.transactionservice.service.TransactionManagementService;
import com.iongroup.transactionservice.service.TransactionRetrievalService;


public class TransactionSerivceEndPoint implements ITransactionServiceEndPoint {
	
	private final ITransactionManagementService traxManagementService;
	private final ITransactionRetrievalService traxRetrievalService;

	public TransactionSerivceEndPoint() {
		this.traxManagementService = new TransactionManagementService();
		this.traxRetrievalService = new TransactionRetrievalService();
	}

	@Override
	public void deposit(Long accountNumber, double amount) throws AccountNotExistException {
		traxManagementService.deposit(accountNumber, amount);
		
	}

	@Override
	public void withdraw(Long accountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {
		traxManagementService.withdraw(accountNumber, amount);
		
	}

	@Override
	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {
		traxManagementService.transfer(fromAccountNumber, toAccountNumber, amount);
		
	}

	@Override
	public double getBalance(Long accountNumber) throws AccountNotExistException {
		return traxRetrievalService.getBalance(accountNumber);
	}

	@Override
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException {
		return traxRetrievalService.getLatestTrasactions(accountNumber);
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate)throws AccountNotExistException, InvalidTimeIntarvalException {
		return traxRetrievalService.getTrasactionsByTimeIntarval(accountNumber, fromDate, toDate);
	}	

}
