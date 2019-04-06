package com.amhfilho.finsys.persistence;

import com.amhfilho.finsys.gui.operation.OperationRepository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionService {
	private OperationRepository operationRepository;
	
	public TransactionService(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}
	
	public List<Transaction> findTransactionsFor(YearMonth yearMonth){
		List<Transaction> transactions = new ArrayList<>();
		List<Operation> operations = operationRepository.findAll();
		operations.forEach(x -> {
			if(x.hasTransactionFor(yearMonth)) {
				transactions.add(x.createTransaction(yearMonth));
			}
		});
		transactions.sort(Comparator.comparing(Transaction::getTransactionDate));
		return transactions;
	}

}
