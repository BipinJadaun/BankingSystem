package com.iongroup.transactionservice.dao;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
		LocalDate traxDate = LocalDate.now();
		Transaction trax = new Transaction(traxId, traxType, amount, traxDate);

		List<Transaction> transactionsList = cache.getTransactions(accountNumber);
		if(transactionsList == null) {
			List<Transaction> transList = new LinkedList<>();
			transList.add(0, trax);
			cache.addTransection(accountNumber, transList);			
		}else {
			transactionsList.add(0,trax);
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
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate) throws AccountNotExistException {

		List<Transaction> traxList = cache.getTransactions(accountNumber);
		List<Transaction> list = null ;
		
		list = traxList.stream().filter(t ->  ((t.getTransactionDate().isEqual(fromDate) ||(t.getTransactionDate().isAfter(fromDate))) &&
				 (t.getTransactionDate().isEqual(toDate) || t.getTransactionDate().isBefore(toDate))))
				.collect(Collectors.toList());
		
		return list;
	}
}
