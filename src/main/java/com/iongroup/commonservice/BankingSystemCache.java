package com.iongroup.commonservice;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iongroup.accountservice.model.Account;
import com.iongroup.transactionservice.model.Transaction;

public class BankingSystemCache {
	
	private static Map<Long, Account> users = new ConcurrentHashMap<>();
	private static Map<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();
	
	

	public void AddAccount(long accountNo, Account user) {
		users.put(accountNo, user);		
	}
	
	public Account getAccount(Long accountNumber) {
		return users.get(accountNumber);
	}

	public void deleteAccount(Long accountNumber) {
		users.remove(accountNumber);
	}

	public void addTransection(long accountNo, List<Transaction> traxList) {
		transactions.put(accountNo, traxList);
	}
	
	public List<Transaction> getTransactions(Long accountNumber) {
		return transactions.get(accountNumber);
	}

	public void deleteTransactions(Long accountNumber) {
		transactions.remove(accountNumber);
		
	}
	
	

}
