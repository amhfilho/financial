package com.amhfilho.finsys.transaction;

import java.time.YearMonth;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll();

    void save(Transaction transaction);

    void remove(Transaction transaction);

    List<Transaction> findByMonth(YearMonth yearMonth);
}
