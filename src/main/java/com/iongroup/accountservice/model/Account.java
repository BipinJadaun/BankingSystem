package com.iongroup.accountservice.model;

import java.util.Date;

public class Account {
	
	private final long accountNo;
	private String accountHolderName;
	private double balance;
	private final Date openingDate;
	
	public Account(Long accountNo, String accountHolderName, double balance) {		
		this.accountNo = accountNo;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
		this.openingDate = new Date();
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountNo ^ (accountNo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNo != other.accountNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", accountHolderName=" + accountHolderName + ", balance=" + balance
				+ ", openingDate=" + openingDate + "]";
	}		
}
