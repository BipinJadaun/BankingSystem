package com.iongroup.transactionservice.model;

import java.time.LocalDate;

public final class Transaction implements Comparable<Transaction>{
	
	private final String id;
	private final TransactionType type;
	private final double amount;
	private final LocalDate transactionDate;

	public Transaction(String id, TransactionType type, double amount, LocalDate transactionDate) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.transactionDate = transactionDate;
		
	}

	public TransactionType getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public String getId() {
		return id;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", type=" + type + ", amount=" + amount + ", traxDate=" + transactionDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Transaction o) {
		return this.getTransactionDate().compareTo(o.getTransactionDate());
	}
}
