package com.amhfilho.finsys;

import com.amhfilho.finsys.transaction.Transaction;

import java.time.YearMonth;
import java.util.List;

public class TransactionForecast {
    private YearMonth yearMonth;

    public TransactionForecast(YearMonth yearMonth){
        this.yearMonth = yearMonth;
    }

    public List<Transaction> transactions(){
        return null;
    }
}
