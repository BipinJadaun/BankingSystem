package com.iongroup.main;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.iongroup.accountservice.exception.AccountAlreadyExistException;
import com.iongroup.accountservice.exception.AccountNotExistException;
import com.iongroup.transactionservice.exception.InsufficientBalanceException;
import com.iongroup.transactionservice.exception.InvalidDateException;
import com.iongroup.transactionservice.model.Transaction;
import com.iongroup.userservice.UserInterface;
import com.iongroup.userservice.UserServiceManager;


public class BankingSystemTest {
	
	private final UserInterface userService;
	
	public BankingSystemTest() {		
		userService = new UserServiceManager();
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
			tranxList = userService.getTrasactionsByTimeIntarval(accountNumber1, fromDate, toDate);
			if (tranxList != null && !tranxList.isEmpty()) {
				System.out.println("Transaction list of account " + accountNumber1);
				for (Transaction tranx : tranxList) {
					System.out.println(tranx.getId() + "  " + tranx.getType() + "  " + tranx.getAmount());
				} 
			}else {
				System.out.println("No Transaction found for given time interval");
			}
		} catch (AccountNotExistException | InvalidDateException e) {
			System.out.println(e.getMessage());
		}
	}

	private void testGetStatement(Long accountNumber1) {
		
		System.out.println("*************************************************************");
		System.out.println("Testing last 10 trasaction statement");
		System.out.println("*************************************************************");
		
		List<Transaction> tranxList;
		try {
			tranxList = userService.getLatestTrasactions(accountNumber1);
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
			accountNumber = userService.createAccount(user, initialAmount);
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
			userService.deposit(accountNumber1, depositAmount);
			System.out.println("current balance of account "+accountNumber1+" is "+ userService.getBalance(accountNumber1));
			
			userService.withdraw(accountNumber1, withdrawAmount);
			System.out.println("current balance of account "+accountNumber1+" is "+ userService.getBalance(accountNumber1));

			userService.transfer(accountNumber1, accountNumber2, transferAmount);
			
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userService.getBalance(accountNumber1));
			System.out.println("current balance of account "+ accountNumber2 +" is "+ userService.getBalance(accountNumber2));

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
			userService.deposit(accountNumber1, depositAmount);
		} 
		catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userService.withdraw(accountNumber1, withdrawAmount);
		} 
		catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		} });

		execute.submit(() -> { try {
			userService.transfer(accountNumber1, accountNumber2, transferAmount);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		} });

		execute.shutdown();

		while (!execute.isTerminated()) {
		}

		try {
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userService.getBalance(accountNumber1));
			System.out.println("current balance of account "+ accountNumber1 +" is "+ userService.getBalance(accountNumber2));
			
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}		
	}

	public void testInvalidTransections(Long accountNumber1, Long accountNumber2, double depositAmount, double withdrawAmount, double transferAmount) {
		System.out.println("*************************************************************");
		System.out.println("Testing Invalid Transections ");
		System.out.println("*************************************************************");
		try {
			userService.closeAccount(accountNumber1);
			userService.closeAccount(accountNumber2);
		} catch (AccountNotExistException e1) {
			System.out.println(e1.getMessage());
		}		

		try {
			userService.deposit(accountNumber1, depositAmount);
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}

		try {
			userService.withdraw(accountNumber1, withdrawAmount);
		} catch (AccountNotExistException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}

		try {
			userService.transfer(accountNumber1, accountNumber2, transferAmount);
		} catch (AccountNotExistException | InsufficientBalanceException e1) {
			System.out.println(e1.getMessage());
		} 

		List<Transaction> tranxList;
		try {
			tranxList = userService.getLatestTrasactions(accountNumber1);
			for(Transaction tranx : tranxList) {
				System.out.println(tranx.getId() + "  " +tranx.getType() + "  " + tranx.getAmount());
			}
		} catch (AccountNotExistException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
