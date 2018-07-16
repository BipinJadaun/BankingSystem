package com.iongroup.accountservice.dao;


import com.iongroup.accountservice.model.Account;
import com.iongroup.dataservice.IBankingSystemCache;


public class AccountDao implements IAccountDao{

	private final IBankingSystemCache cache;

	public AccountDao(IBankingSystemCache cache) {
		this.cache = cache;
	}

	@Override
	public Account getAccount(Long accountNumber) {
		return cache.getAccount(accountNumber);
	}
	
	@Override
	public void createAccount(Account user) {
		cache.AddAccount(user.getAccountNo(), user);
	}
	
	@Override
	public void closeAccount(Long accountNumber) {
		cache.deleteAccount(accountNumber);
	}
}
