package com.iongroup.transactionservice.service;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.transactionservice.model.Transaction;

public class TransactionServiceHelper {

	public int searchTransactionIndexForDate(List<Transaction> list, LocalDate date, String dateType) {
		Transaction first = list.get(0);
		Transaction last = list.get(list.size()-1);
		int idx = -1;

		if(date.isBefore(first.getTransactionDate()))
			return 0;

		if(date.isAfter(last.getTransactionDate()))
			return -1;       

		int low = 0;
		int high = list.size() - 1;

		while (low <= high) {
			int mid = (high + low) / 2;
			Transaction midTrax = list.get(mid);

			if(date.isEqual(midTrax.getTransactionDate())) {
				idx = mid;
				if(dateType.equals("START_DATE"))
					high = mid-1;
				else if(dateType.endsWith("END_DATE"))
					low = mid+1;
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

		if(dateType.equals("START_DATE")) {
			idx = low;
		}else if(dateType.equals("END_DATE")) {
			idx = high;
		}
		return idx;
	}
}
