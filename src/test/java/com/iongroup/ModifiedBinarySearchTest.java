package com.iongroup;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ModifiedBinarySearchTest {

	public static void main(String[] args) {
		List<Transaction> list = new LinkedList<>();
		list.add(new Transaction("1", "DEPOSITE", 800, LocalDate.now().minusDays(10)));
		list.add(new Transaction("2", "WITHDRAW", 100, LocalDate.now().minusDays(5)));
		list.add(new Transaction("3", "DEPOSITE", 100, LocalDate.now().plusDays(20)));
		list.add(new Transaction("4", "WITHDRAW", 200, LocalDate.now().plusMonths(1)));
		list.add(new Transaction("5", "WITHDRAW", 100, LocalDate.now().minusMonths(1)));
		list.add(new Transaction("6", "DEPOSITE", 500, LocalDate.now().plusDays(2)));
		list.add(new Transaction("7", "WITHDRAW", 100, LocalDate.now()));
		list.add(new Transaction("8", "DEPOSITE", 100, LocalDate.now()));
		list.add(new Transaction("9", "DEPOSITE", 100, LocalDate.now()));

		Collections.sort(list);

		System.out.println("\n\t All Transaction :");
		System.out.println("********************************************************************");

		for(Transaction tx : list)
			System.out.println(tx);

		//LocalDate startDate = LocalDate.now();
		//LocalDate endDate = LocalDate.now();

		LocalDate startDate = LocalDate.of(2018, Month.JUNE, 12);
		LocalDate endDate = LocalDate.of(2018, Month.JULY, 12);

		if(!endDate.isBefore(startDate)) {

			int startDateIdx = search(list, startDate, "START_DATE");
			int endDateIdx = search(list, endDate, "END_DATE");

			if(startDateIdx != -1 && endDateIdx != -1) {
				List<Transaction> subList = list.subList(startDateIdx, endDateIdx+1);				
				if (subList != null && !subList.isEmpty()) {
					System.out.println("\n\t Transaction Between Given Time Intarval :");
					System.out.println("********************************************************************");
					for (Transaction tx : subList)
						System.out.println(tx);
				}else{
					System.out.println("No Transaction Found For Fiven Time Intarval");
				}
			}else{
				System.out.println("Invalid From or To date");
			}
		}else {
			System.out.println("To date must be equal or greater than From Date");
		}
	}

	public static int search(List<Transaction> list, LocalDate date, String dateType) {
		
		int idx = -1;
		int low = 0;
		int high = list.size() - 1;

		while (low <= high) {
			int mid = (high + low) / 2;
			Transaction midTrax = list.get(mid);

			if(date.isEqual(midTrax.getTransactionDate())) {
				idx = mid;
				if(dateType.equals("START_DATE"))
					high = mid-1;                       //to find first occurrence in case of duplicates (multiple transaction on same date)
				else if(dateType.endsWith("END_DATE"))
					low = mid+1;						//to find last occurrence in case of duplicates (multiple transaction on same date)
			}
			else if(date.isBefore(midTrax.getTransactionDate())) {
				high = mid-1;
			}
			else{
				low = mid + 1;
			}
		}

		if(idx != -1) {
			return idx; 
		}

		if(dateType.equals("START_DATE"))
			idx = low;							//to return next immediate transaction if there is no transaction on given fromDate
		else if(dateType.equals("END_DATE"))
			idx = high;							//to return previous immediate transaction if there is no transaction on given toDate

		return idx;
	}
}

class Transaction implements Comparable<Transaction>{

	private final String id;
	private final String type;
	private final double amount;
	private final LocalDate transactionDate;

	public Transaction(String id, String type, double amount, LocalDate transactionDate) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.transactionDate = transactionDate;

	}

	public String getType() {
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
	public int compareTo(Transaction o) {
		return this.getTransactionDate().compareTo(o.getTransactionDate());
	}
}

