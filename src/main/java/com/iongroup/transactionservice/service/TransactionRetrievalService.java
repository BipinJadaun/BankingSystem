package com.iongroup.transactionservice.service;

import java.util.Date;
import java.util.List;

import com.iongroup.accountservice.dao.AccountDao;
import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.accountservice.service.AccountValidationService;
import com.iongroup.accountservice.service.IAccountValidationService;
import com.iongroup.transactionservice.dao.ITransactionDao;
import com.iongroup.transactionservice.dao.TransactionDao;
import com.iongroup.transactionservice.exception.InvalidTimeIntarvalException;
import com.iongroup.transactionservice.model.Transaction;

public class TransactionRetrievalService implements ITransactionRetrievalService {

	private final IAccountValidationService accValidationService;
	private final ITransactionValidationService traxValidationService;
	private final ITransactionDao transectionDao;
	private final IAccountDao accountDao;


	public TransactionRetrievalService() {
		this.accValidationService = new AccountValidationService();
		this.transectionDao = new TransactionDao();
		this.accountDao = new AccountDao();
		this.traxValidationService = new TransactionValidationService();
	}

	public double getBalance(Long accountNumber) throws AccountNotExistException {
		double balance = 0;
		if(accValidationService.isValidAccount(accountNumber)) {
			Account account = accountDao.getAccount(accountNumber);
			synchronized (account) {
				balance = transectionDao.getBalance(accountNumber);
			}			
		}else {
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
		}
		return balance;
	}

	@Override
	public List<Transaction> getLatestTrasactions(Long accountNumber) throws AccountNotExistException {
		List<Transaction> trax = null;
		if(accValidationService.isValidAccount(accountNumber)) {
			Account account = accountDao.getAccount(accountNumber);
			synchronized (account) {
				trax = transectionDao.getLatestTrasactions(accountNumber);
			}
		}
		else {
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
		}
		return trax;
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, Date fromDate, Date toDate) throws AccountNotExistException, InvalidTimeIntarvalException {

		List<Transaction> trax = null;
		if (accValidationService.isValidAccount(accountNumber)) {
			Account account = accountDao.getAccount(accountNumber);
			if (traxValidationService.isValidIntarval(account, fromDate, toDate)) {				
				synchronized (account) {
					trax = transectionDao.getTrasactionsByTimeIntarval(accountNumber, fromDate, toDate);
				}
			}else {
				throw new InvalidTimeIntarvalException("Given dates are invalid"); 
			}
		}else {
			throw new AccountNotExistException("Account " + accountNumber + " does not exist");
		}
		return trax;
	}
}
