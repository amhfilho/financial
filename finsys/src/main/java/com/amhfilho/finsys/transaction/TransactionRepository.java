package com.amhfilho.finsys.transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll();

    void save(Transaction transaction);

    void remove(Transaction transaction);
}
