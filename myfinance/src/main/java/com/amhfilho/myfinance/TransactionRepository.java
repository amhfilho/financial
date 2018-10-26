package com.amhfilho.myfinance;

import java.time.YearMonth;
import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll();

    List<Transaction> findByMonth(YearMonth month);
}
