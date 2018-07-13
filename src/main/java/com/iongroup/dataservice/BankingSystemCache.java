package com.iongroup.dataservice;

import java.util.List;

import com.iongroup.accountservice.model.Account;
import com.iongroup.transactionservice.model.Transaction;

public class BankingSystemCache implements IBankingSystemCache{
	
	private final AccountCache accountcache;
	private final TransactionCache traxCache;
		
	public BankingSystemCache() {
		this.accountcache = new AccountCache();
		this.traxCache = new TransactionCache();
	}

	@Override
	public void AddAccount(long accountNo, Account user) {
		accountcache.AddAccount(accountNo, user);		
	}
	
	@Override
	public Account getAccount(Long accountNumber) {
		return accountcache.getAccount(accountNumber);
	}
	
	@Override
	public void deleteAccount(Long accountNumber) {
		accountcache.deleteAccount(accountNumber);
	}
	
	@Override
	public void addTransection(long accountNo, List<Transaction> traxList) {
		traxCache.addTransection(accountNo, traxList);
	}
	
	@Override
	public List<Transaction> getTransactions(Long accountNumber) {
		return traxCache.getTransactions(accountNumber);
	}
	
	@Override
	public void deleteTransactions(Long accountNumber) {
		traxCache.deleteTransactions(accountNumber);
		
	}
	
	

}
