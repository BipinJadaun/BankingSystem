package com.iongroup.transactionservice.dao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.dataservice.IBankingSystemCache;
import com.iongroup.transactionservice.model.Transaction;
import com.iongroup.transactionservice.model.TransactionType;
import com.iongroup.transactionservice.service.TransactionIdGenerator;
import com.iongroup.transactionservice.service.TransactionServiceHelper;

public class TransactionDao implements ITransactionDao{

	private final TransactionIdGenerator idGenerator;
	private final IBankingSystemCache systemCache;
	private final TransactionServiceHelper helper;


	public TransactionDao(IBankingSystemCache systemCache) {
		this.systemCache = systemCache;
		this.idGenerator = new TransactionIdGenerator();
		this.helper = new TransactionServiceHelper();
	}

	@Override
	public void deposit(Long accountNumber, double amount) {
		Account userAccount = systemCache.getAccount(accountNumber);
		double currentBalance = userAccount.getBalance();
		userAccount.setBalance(currentBalance + amount);

		addTransection(accountNumber, TransactionType.DEPOSITE, amount);
	}

	@Override
	public void withdraw(Long accountNumber, double amount) {
		Account userAccount = systemCache.getAccount(accountNumber);
		double currentBalance = userAccount.getBalance();
		userAccount.setBalance(currentBalance - amount);

		addTransection(accountNumber, TransactionType.WITHDRAW, amount);	
	}

	@Override
	public double getBalance(Long accountNumber) {
		Account userAccount = systemCache.getAccount(accountNumber);
		return userAccount.getBalance();		
	}

	@Override
	public void addTransection(Long accountNumber, TransactionType traxType, double amount) {
		String traxId = idGenerator.createID();
		LocalDate traxDate = LocalDate.now();
		Transaction trax = new Transaction(traxId, traxType, amount, traxDate);

		List<Transaction> transactionsList = systemCache.getTransactions(accountNumber);
		if(transactionsList == null) {
			List<Transaction> transList = new LinkedList<>();
			transList.add(0, trax);
			systemCache.addTransection(accountNumber, transList);			
		}else {
			transactionsList.add(0,trax);
		}		
	}

	@Override
	public void removeTransactions(Long accountNumber) {
		if(systemCache.getAccount(accountNumber) != null) {
			synchronized(accountNumber) {
				systemCache.deleteTransactions(accountNumber);
			}
		}
	}	

	@Override
	public List<Transaction> getLastTenTrasactions(Long accountNumber){
		int count = 0;
		List<Transaction> list = new LinkedList<>();
		List<Transaction> TranxList = systemCache.getTransactions(accountNumber);

		while(count < 10 && count < TranxList.size()) {
			list.add(0, TranxList.get(count));
			count++;
		}
		return list;
	}

	@Override
	public List<Transaction> getTrasactionsByTimeIntarval(Long accountNumber, LocalDate fromDate, LocalDate toDate) throws AccountNotExistException {

		List<Transaction> traxList = systemCache.getTransactions(accountNumber);
		List<Transaction> list = null ;
		
		int startIdx = helper.searchTransactionIndexForDate(traxList, fromDate, "START_DATE");
		int endIdx = helper.searchTransactionIndexForDate(traxList, toDate, "END_DATE");
		
		if(startIdx == -1) {
			System.out.println("Invalid From Date");
			return null;
		}
		if(endIdx == -1) {
			System.out.println("Invalid To Date");
			return null;
		}		
		list = traxList.subList(startIdx, endIdx+1);
		
		Collections.reverse(list);
		
		return list;
	}
}
