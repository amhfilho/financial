package com.amhfilho.finsys.persistence;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.amhfilho.finsys.gui.operation.OperationRepository;

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
		return transactions;
	}
	
//	private EntityManager em;
//	
//	public TransactionService(EntityManager em) {
//		this.em = em;
//	}
	/*
	public Object[][] getData() {
		List<Transaction> transactions = new ArrayList<>();
		transactions = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		Object[][] data = new Object[transactions.size()][3];
		for(int i = 0 ; i < transactions.size(); i++) {
			Transaction t = transactions.get(i);
			data[i][0] = t.getTransactionDate();
			data[i][1] = t.getDescription();
			data[i][2] = t.getAmount();
		}
		return data;
	}
	
	public List<Transaction> findByMonth(YearMonth yearMonth){
		Query q = em.createQuery("select t from Transaction t where t.transactionDate between :begin and :end", Transaction.class);
		LocalDate init = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
		LocalDate begin = init.withDayOfMonth(1);
		LocalDate end = init.withDayOfMonth(init.lengthOfMonth());
		q.setParameter("begin", begin);
		q.setParameter("end",   end);
		return q.getResultList();
	}
	
	public void save(Transaction t) {
		em.getTransaction().begin();
		if(t.getId() == null) em.persist(t);
		else em.merge(t);
		em.getTransaction().commit();
	}
	
	public List<Transaction> getAll(){
		return em.createQuery("select t from Transaction t", Transaction.class).getResultList();
	}
	*/

}
