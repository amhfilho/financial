package com.amhfilho.finsys.transaction;

import javax.persistence.EntityManager;
import java.util.List;

public class DatabaseTransactionRepository implements TransactionRepository {

    private EntityManager em;

    public DatabaseTransactionRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public List<Transaction> findAll() {
        return em.createQuery("from Transaction t").getResultList();
    }

    @Override
    public void save(Transaction transaction) {
        em.merge(transaction);
    }

    @Override
    public void remove(Transaction transaction) {
        em.remove(transaction);
    }
}
