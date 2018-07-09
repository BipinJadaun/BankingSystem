package com.iongroup.common;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.model.Transaction;


public class BankingSystem {
	
	private final UserEndPoint userEndPoint;
	
	public BankingSystem() {		
		userEndPoint = new UserEndPoint();
	}
	
	public void test() {
		
		Long accountNumber1 = testAccountService("Bipin", 1000);
		
		Long accountNumber2 = testAccountService("Anil", 2000);
		
		testTransections(accountNumber1, accountNumber2, 600, 300, 400);
		
		testConcurrentTransections(accountNumber1, accountNumber2, 700, 250, 400);
		
		testGetStatement(accountNumber1);
		
		testGetStatementByIntarval(accountNumber1, LocalDate.now(), LocalDate.now());
		
		testInvalidTransections(accountNumber1, accountNumber2, 1200, 900, 300);
	}


	private void testGetStatementByIntarval(Long accountNumber1, LocalDate fromDate, LocalDate toDate) {
		System.out.println("*************************************************************");
		System.out.println("Testing trasaction statement by given intaraval");
		System.out.println("*************************************************************");
		
		List<Transaction> tranxList;
		try {
			tranxList = userEndPoint.getTrasactionsByTimeIntarval(accountNumber1, fromDate, toDate);
			if (tranxList != null && !tranxList.isEmpty()) {
				System.out.println("Transaction list of account " + accountNumber1);
				for (Transaction tranx : tranxList) {
					System.out.println(tranx.getId() + "  " + tranx.getType() + "  " + tranx.getAmount());
				} 
			}
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}
	}

	private void testGetStatement(Long accountNumber1) {
		
		System.out.println("*************************************************************");
		System.out.println("Testing last 10 trasaction statement");
		System.out.println("*************************************************************");
		
		List<Transaction> tranxList;
		try {
			tranxList = userEndPoint.getLatestTrasactions(accountNumber1);
			if (tranxList != null && !tranxList.isEmpty()) {
				System.out.println("Transaction list of account " + accountNumber1);
				for (Transaction tranx : tranxList) {
					System.out.println(tranx.getId() + "  " + tranx.getType() + "  " + tranx.getAmount());
				} 
			}
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}				
	}

	public Long testAccountService(String user, double initialAmount) {
		
		Long accountNumber = null;
		
		try {
			accountNumber = userEndPoint.createAccount(user, initialAmount);
		} 
		catch (AccountAlreadyExistException e1) {
			System.out.println(e1.getMessage());
		}		
		return accountNumber;
		
	}
	
	public void testTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {		
		System.out.println("*************************************************************");
		System.out.println("Testing Transactions");
		System.out.println("*************************************************************");
		
		try {
			userEndPoint.deposit(accountNumber1, depositAmount);
			System.out.println("current balance of account "+accountNumber1+" is "+ userEndPoint.getBalance(accountNumber1));
			
			userEndPoint.withdraw(accountNumber1, withdrawAmount);
			System.out.println("current balance of account "+accountNumber1+" is "+ userEndPoint.getBalance(accountNumber1));

			userEndPoint.transfer(accountNumber1, accountNumber2, transferAmount);
			
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userEndPoint.getBalance(accountNumber1));
			System.out.println("current balance of account "+ accountNumber2 +" is "+ userEndPoint.getBalance(accountNumber2));

		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testConcurrentTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {
		
		System.out.println("*************************************************************");
		System.out.println("Testing Concurrent Transactions");
		System.out.println("*************************************************************");

		ExecutorService execute = Executors.newFixedThreadPool(3);

		execute.submit(() -> { try {
			userEndPoint.deposit(accountNumber1, depositAmount);
		} 
		catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userEndPoint.withdraw(accountNumber1, withdrawAmount);
		} 
		catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userEndPoint.transfer(accountNumber1, accountNumber2, transferAmount);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		} });

		execute.shutdown();

		while (!execute.isTerminated()) {
		}

		try {
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userEndPoint.getBalance(accountNumber1));
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userEndPoint.getBalance(accountNumber2));
			
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}		
	}

	public void testInvalidTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {
		System.out.println("*************************************************************");
		System.out.println("Testing Invalid Transections ");
		System.out.println("*************************************************************");
		try {
			userEndPoint.closeAccount(accountNumber1);
			userEndPoint.closeAccount(accountNumber2);
		} catch (AccountNotExistException e1) {
			System.out.println(e1.getMessage());
		}		

		try {
			userEndPoint.deposit(accountNumber1, depositAmount);
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}

		try {
			userEndPoint.withdraw(accountNumber1, withdrawAmount);
		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}

		try {
			userEndPoint.transfer(accountNumber1, accountNumber2, transferAmount);
		} catch (AccountNotExistException | InsufficientBalanceException e1) {
			System.out.println(e1.getMessage());
		} 

		List<Transaction> tranxList;
		try {
			tranxList = userEndPoint.getLatestTrasactions(accountNumber1);
			for(Transaction tranx : tranxList) {
				System.out.println(tranx.getId() + "  " +tranx.getType() + "  " + tranx.getAmount());
			}
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
