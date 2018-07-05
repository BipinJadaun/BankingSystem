package com.iongroup;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class ABC {

	public static void main(String[] args) {
		List<Transaction> list = new LinkedList<>();
		list.add(new Transaction(1, new Date(), 100));
		list.add(new Transaction(2, new Date(), 200));
		list.add(new Transaction(3, new Date(), 300));
		list.add(new Transaction(4, new Date(), 400));
		list.add(new Transaction(5, new Date(), 500));
		list.add(new Transaction(6, new Date(), 600));
		list.add(new Transaction(7, new Date(), 700));
		
		Calendar  cal = Calendar.getInstance();

		System.out.println(cal.get(Calendar.YEAR));
		System.out.println(cal.get(Calendar.MONTH));
		System.out.println(cal.get(Calendar.DATE));
		System.out.println(cal.get(Calendar.HOUR));
		System.out.println(cal.get(Calendar.MINUTE));
		System.out.println(cal.get(Calendar.SECOND));
		
	}
}



class Transaction implements Comparable<Transaction>{
	
	private final int id;
	private final  Date date;
	private final double amount;


	public Transaction(int id, Date date, double amount) {
		this.id = id;
		this.date = date;
		this.amount = amount;
	}	
	
	
	public int getId() {
		return id;
	}


	public Date getDate() {
		return date;
	}


	public double getAmount() {
		return amount;
	}


	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount=" + amount + "]";
	}


	@Override
	public int compareTo(Transaction t) {

		if(this.getDate().compareTo(t.getDate()) < 0)
			return -1;
		else if (this.getDate().compareTo(t.getDate()) > 0)
			return 1;
		else
			return 0;
	}
	
}
