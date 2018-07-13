package com.iongroup.dataservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iongroup.accountservice.model.Account;

public class AccountCache {
	private Map<Long, Account> users = new ConcurrentHashMap<>();

	public void AddAccount(long accountNo, Account user) {
		users.put(accountNo, user);		
	}

	public void deleteAccount(Long accountNumber) {
		users.remove(accountNumber);
	}

	public Account getAccount(Long accountNumber) {		
		return users.get(accountNumber);
	}

	
}
