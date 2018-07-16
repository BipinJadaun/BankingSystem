package com.iongroup.transactionservice.service;

import java.time.LocalDate;
import java.util.List;

import com.iongroup.transactionservice.model.Transaction;

public class TransactionServiceHelper {

	public int searchTransactionIndexForDate(List<Transaction> list, LocalDate date, String dateType) {

		int idx = -1;
		int low = 0;
		int high = list.size() - 1;

		while (low <= high) {
			int mid = (high + low) / 2;
			Transaction midTrax = list.get(mid);

			if(date.isEqual(midTrax.getTransactionDate())) {
				idx = mid;
				if(dateType.equals("START_DATE"))
					high = mid-1; 						//to find first occurrence in case of duplicates (multiple transaction on same date)
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
		if(dateType.equals("START_DATE")) {
			idx = low;								//to return next transaction's index if there is no transaction on given fromDate
		}else if(dateType.equals("END_DATE")) {
			idx = high;								//to return previous transaction's index if there is no transaction on given toDate
		}
		return idx;
	}
}
