package com.amhfilho.finsys.persistence;

import com.amhfilho.finsys.gui.transaction.TransactionRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class DatabaseTransactionRepository implements TransactionRepository {
    private EntityManager em;

    public DatabaseTransactionRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Transaction save(Transaction transaction) {
        em.getTransaction().begin();
        if(transaction.getId() == null){
            em.persist(transaction);
        } else {
            transaction = em.merge(transaction);
        }
        em.getTransaction().commit();
        return transaction;
    }

    @Override
    public List<Transaction> findByMonth(YearMonth yearMonth) {
        LocalDate start = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate end   = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        String sql = "select t from Transaction t where t.transactionDate between :start and :end order by t.transactionDate";
        Query q = em.createQuery(sql,Transaction.class);
        q.setParameter("start", start);
        q.setParameter("end", end);
        return q.getResultList();
    }

    @Override
    public List<Transaction> findLate(YearMonth yearMonth) {
        LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        String sql = "select t from Transaction t where t.transactionDate < :date";
        Query q = em.createQuery(sql, Transaction.class);
        q.setParameter("date",date);
        return q.getResultList();
    }
}
