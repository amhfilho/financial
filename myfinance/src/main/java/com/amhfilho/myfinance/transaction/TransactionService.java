package com.amhfilho.myfinance.transaction;

import java.time.YearMonth;
import java.util.List;

public interface TransactionService {
    List<Transaction> findByMonth(YearMonth month);
}
