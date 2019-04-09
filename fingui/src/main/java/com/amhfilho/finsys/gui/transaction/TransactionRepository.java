package com.amhfilho.finsys.gui.transaction;

import com.amhfilho.finsys.persistence.Transaction;

import java.time.YearMonth;
import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    List<Transaction> findByMonth(YearMonth yearMonth);
}
