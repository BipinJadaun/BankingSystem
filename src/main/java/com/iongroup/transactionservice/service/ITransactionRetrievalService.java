package com.iongroup.transactionservice.service;

import java.util.Date;
import java.util.List;

import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InvalidTimeIntarvalException;
import com.iongroup.transactionservice.model.Transaction;

public interface ITransactionRetrievalService {
	
	public double getBalance(Long accountNumber) throws AccountNotExistException;
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException;
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, Date fromDate, Date toDate) throws AccountNotExistException, InvalidTimeIntarvalException;

}
