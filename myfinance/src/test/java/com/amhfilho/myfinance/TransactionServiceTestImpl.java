package com.amhfilho.myfinance;

import com.amhfilho.myfinance.transaction.Transaction;
import com.amhfilho.myfinance.transaction.TransactionRepository;
import com.amhfilho.myfinance.transaction.TransactionService;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceTestImpl implements TransactionService {

    private TransactionRepository repository;

    public TransactionServiceTestImpl(TransactionRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Transaction> findByMonth(YearMonth month) {
        return this.repository.findAll()
                .stream()
                .filter(t -> t.getDate().getYear()==month.getYear() && t.getDate().getMonth()==month.getMonth())
                .collect(Collectors.toList());
    }
}
