package com.iongroup.transactionservice.service;

import com.iongroup.accountservice.dao.IAccountDao;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.accountservice.model.Account;
import com.iongroup.accountservice.service.AccountValidationService;
import com.iongroup.accountservice.service.IAccountValidationService;
import com.iongroup.transactionservice.dao.ITransactionDao;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;


public class TransactionManagementService implements ITransactionManagementService{

	private final IAccountValidationService accValidationService;
	private final ITransactionValidationService transValidationService;
	private final ITransactionDao transectionDao;
	private final IAccountDao accountDao;

	public TransactionManagementService(IAccountDao accountDao, ITransactionDao transectionDao) {
		this.transectionDao = transectionDao;
		this.accountDao = accountDao;
		this.accValidationService = new AccountValidationService(accountDao);
		this.transValidationService = new TransactionValidationService(accountDao);
	}

	public void deposit(Long accountNumber, double amount) throws AccountNotExistException{

		accValidationService.validateAccount(accountNumber);		
		Account account = accountDao.getAccount(accountNumber);
		
		account.getLock().lock();
		try {
			transectionDao.deposit(accountNumber, amount);
			System.out.println("Amount: " + amount +" Deposited to Account - " + accountNumber);
		}finally {
			account.getLock().unlock();
		}
	}

	public void withdraw(Long accountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException{

		accValidationService.validateAccount(accountNumber);
		Account account = accountDao.getAccount(accountNumber);
		
		account.getLock().lock();
		try{
			transValidationService.validateTransaction(accountNumber, amount);
			transectionDao.withdraw(accountNumber, amount);
			System.out.println("Amount: " + amount +" Withdrawn From Account - " + accountNumber);
		}finally {
			account.getLock().unlock();
		}
	}


	public void transfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException {

		validateTransfer(fromAccountNumber, toAccountNumber, amount);
		
		Account fromAccount = accountDao.getAccount(fromAccountNumber);
		Account toAccount = accountDao.getAccount(toAccountNumber);
		
		while(true) {
			if(fromAccount.getLock().tryLock()) {
				try {
					if(toAccount.getLock().tryLock()) {
						try {
							transectionDao.withdraw(fromAccountNumber, amount);
							System.out.println("Amount: " + amount +" Withdrawn From Account - " + fromAccountNumber);

							transectionDao.deposit(toAccountNumber, amount);
							System.out.println("Amount: " + amount +" Deposited to Account - " + toAccountNumber);
							
							break;
						}finally {
							toAccount.getLock().unlock();
						}
					}
				}finally {
					fromAccount.getLock().unlock();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}			
	}

	private void validateTransfer(Long fromAccountNumber, Long toAccountNumber, double amount) throws AccountNotExistException, InsufficientBalanceException  {
		accValidationService.validateAccount(fromAccountNumber);			
		transValidationService.validateTransaction(fromAccountNumber, amount);
		accValidationService.validateAccount(toAccountNumber);			
	}

}
