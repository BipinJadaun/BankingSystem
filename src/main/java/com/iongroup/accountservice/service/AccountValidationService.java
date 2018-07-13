package com.iongroup.accountservice.service;

import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;

public class AccountValidationService implements IAccountValidationService {

	private IAccountDao accountDao;	

	public AccountValidationService(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public void validateAccount(Long accountNumber) throws AccountNotExistException {
		if(accountDao.getAccount(accountNumber) == null)
			throw new AccountNotExistException("Account "+accountNumber + " does not exist");
	}

	@Override
	public void validateCreateAccount(Long accountNumber) throws AccountAlreadyExistException {
		if(accountDao.getAccount(accountNumber) != null)
			throw new AccountAlreadyExistException("Account "+accountNumber + " already exist");
	}
}
