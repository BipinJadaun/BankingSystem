package com.iongroup.accountservice.service;

import com.iongroup.accountservice.dao.AccountDao;
import com.iongroup.accountservice.dao.IAccountDao;

public class AccountValidationService implements IAccountValidationService {
	
	private IAccountDao accountDao;	
	
	public AccountValidationService() {
		this.accountDao = new AccountDao();
	}

	@Override
	public boolean isValidAccount(Long accountNumber) {
		return isAccountExist(accountNumber);
	}

	private boolean isAccountExist(Long accountNumber) {
		if(accountDao.getAccount(accountNumber) != null) {
			return true;
		}
		return false;
	}	
}
