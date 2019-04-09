package com.amhfilho.finsys.persistence;

import com.amhfilho.finsys.gui.operation.OperationRepository;
import com.amhfilho.finsys.gui.transaction.TransactionRepository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionService {
	private OperationRepository operationRepository;
	private TransactionRepository transactionRepository;
	
	public TransactionService(OperationRepository operationRepository, TransactionRepository transactionRepository) {
		this.operationRepository = operationRepository;
		this.transactionRepository = transactionRepository;
	}
	
	public List<Transaction> findTransactionsFor(YearMonth yearMonth){
		List<Transaction> transactions = transactionRepository.findByMonth(yearMonth);
		List<Operation> operations = operationRepository.findAll();
		operations.forEach(x -> {
			if(x.hasTransactionFor(yearMonth) && !contains(x, transactions)) {
				transactions.add(x.createTransaction(yearMonth));
			}
		});
		transactions.sort(Comparator.comparing(Transaction::getTransactionDate));
		return transactions;
	}

	private boolean contains(Operation operation, List<Transaction> transactions){
		for(Transaction t: transactions){
			if(t.getOperation().getId().equals(operation.getId())){
				return true;
			}
		}
		return false;
	}

	public Transaction save(Transaction transaction){
		return transactionRepository.save(transaction);
	}

}
