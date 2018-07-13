package com.iongroup.dataservice;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iongroup.transactionservice.model.Transaction;

public class TransactionCache {
	
	private Map<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();

	public void deleteTransactions(Long accountNumber) {
		transactions.remove(accountNumber);		
	}

	public List<Transaction> getTransactions(Long accountNumber) {
		return transactions.get(accountNumber);
	}

	public void addTransection(long accountNo, List<Transaction> traxList) {
		transactions.put(accountNo, traxList);
	}

}
