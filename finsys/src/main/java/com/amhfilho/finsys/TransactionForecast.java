package com.amhfilho.finsys;

import com.amhfilho.finsys.transaction.Transaction;
import com.amhfilho.finsys.transaction.TransactionRepository;

import java.time.YearMonth;
import java.util.List;

public class TransactionForecast {
    private YearMonth yearMonth;
    private TransactionRepository transactionRepository;

    public TransactionForecast(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> transactionsOf(YearMonth yearMonth){
        return null;
    }
}
