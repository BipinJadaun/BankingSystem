package com.iongroup.transactionservice.dao;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.commonservice.BankingSystemCache;
import com.iongroup.transactionservice.model.Transaction;
import com.iongroup.transactionservice.model.TransactionType;
import com.iongroup.transactionservice.service.TransactionIdGenerator;

public class TransactionDao implements ITransactionDao{
	
	private final TransactionIdGenerator idGenerator;
	private final BankingSystemCache cache;

	
	public TransactionDao() {
		this.idGenerator = new TransactionIdGenerator();
		this.cache = new BankingSystemCache();		
	}

	@Override
	public void deposit(Long accountNumber, double amount) {
		Account userAccount = cache.getAccount(accountNumber);
		double currentBalance = userAccount.getBalance();
		userAccount.setBalance(currentBalance + amount);

		addTransection(accountNumber, TransactionType.DEPOSITE, amount);
	}

	@Override
	public void withdraw(Long accountNumber, double amount) {
		Account userAccount = cache.getAccount(accountNumber);
		double currentBalance = userAccount.getBalance();
		userAccount.setBalance(currentBalance - amount);
		
		addTransection(accountNumber, TransactionType.WITHDRAW, amount);	
	}

	@Override
	public double getBalance(Long accountNumber) {
		Account userAccount = cache.getAccount(accountNumber);
		return userAccount.getBalance();		
	}
	
	@Override
	public void addTransection(Long accountNumber, TransactionType traxType, double amount) {
		String traxId = idGenerator.createID();
		Date traxDate = new Date();
		Transaction trax = new Transaction(traxId, traxType, amount, traxDate);
		
		List<Transaction> transactionsList = cache.getTransactions(accountNumber);
		if(transactionsList == null) {
			List<Transaction> transList = new LinkedList<>();
			transList.add(0, trax);
			cache.addTransection(accountNumber, transList);			
		}else {
			transactionsList.add(trax);
		}		
	}
	
	@Override
	public void removeTransactions(Long accountNumber) {
		if(cache.getAccount(accountNumber) != null) {
			synchronized(accountNumber) {
				cache.deleteTransactions(accountNumber);
			}
		}
	}	
	
	@Override
	public List<Transaction> getLatestTrasactions(Long accountNumber){
		int count = 0;
		List<Transaction> list = new LinkedList<>();
		List<Transaction> TranxList = cache.getTransactions(accountNumber);
		
		while(count < 10 && count < TranxList.size()) {
			list.add(0, TranxList.get(count));
			count++;
		}
		return list;
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, Date fromDate, Date toDate) throws AccountNotExistException {
		
		List<Transaction> list = new LinkedList<>();
		List<Transaction> traxList = cache.getTransactions(accountNumber);
		
		
		
		for(Transaction tr: traxList) {
			if(tr.getTransactionDate().compareTo(fromDate) >= 0 && tr.getTransactionDate().compareTo(toDate) <=0 ) {
				list.add(tr);
			}
		}		
		return list;
	}
}
