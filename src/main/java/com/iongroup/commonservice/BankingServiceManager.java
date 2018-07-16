package com.iongroup.commonservice;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.accountservice.dao.AccountDao;
import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.endpoint.AccountServiceInterface;
import com.iongroup.accountservice.endpoint.AccountServiceManager;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.dataservice.BankingSystemCache;
import com.iongroup.dataservice.IBankingSystemCache;
import com.iongroup.transactionservice.endpoint.TransactionServiceInterface;
import com.iongroup.transactionservice.dao.ITransactionDao;
import com.iongroup.transactionservice.dao.TransactionDao;
import com.iongroup.transactionservice.endpoint.TransactionSerivceManager;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidTimeIntarvalException;
import com.iongroup.transactionservice.model.Transaction;

public class BankingServiceManager implements BankingSystemInterface{
	
	private final AccountServiceInterface accountService;
	private final TransactionServiceInterface transactionService;
	private final IAccountDao accDao;
	private final ITransactionDao traxDao;
	
	public BankingServiceManager() {
		IBankingSystemCache systemCache = new BankingSystemCache();
		this.accDao = new AccountDao(systemCache);
		this.traxDao = new TransactionDao(systemCache);
		this.accountService = new AccountServiceManager(accDao, traxDao);
		this.transactionService = new TransactionSerivceManager(accDao, traxDao);
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
	public void withdraw(Long accountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {
		transactionService.withdraw(accountNumber, amount);
	}

	@Override
	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount)throws AccountNotExistException, InsufficientBalanceException {
		transactionService.transfer(fromAccountNumber, toAccountNumber, amount);
	}

	@Override
	public double getBalance(Long accountNumber) throws AccountNotExistException {
		return transactionService.getBalance(accountNumber);
	}

	@Override
	public List<Transaction> getLastTenTrasactions(Long accountNumber) throws AccountNotExistException {
		return transactionService.getLastTenTrasactions(accountNumber);
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate) throws AccountNotExistException, InvalidTimeIntarvalException {
		return transactionService.getTrasactionsByTimeIntarval(accountNumber, fromDate, toDate);
	}

}
