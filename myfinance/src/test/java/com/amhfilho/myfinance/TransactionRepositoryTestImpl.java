package com.amhfilho.myfinance;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepositoryTestImpl implements TransactionRepository {

    private List<Transaction> transactions;

    public TransactionRepositoryTestImpl(List<Transaction> transactions){
        this.transactions = transactions;
    }

    @Override
    public List<Transaction> findAll() {
        return this.transactions;
    }

    @Override
    public List<Transaction> findByMonth(YearMonth month) {
        return this.transactions
                .stream()
                .filter(t -> t.getDate().getYear()==month.getYear() && t.getDate().getMonth()==month.getMonth())
                .collect(Collectors.toList());
    }
}
