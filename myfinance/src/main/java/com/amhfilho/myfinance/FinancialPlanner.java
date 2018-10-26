package com.amhfilho.myfinance;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialPlanner {

    private TransactionRepository transactionRepository;
    private OperationRepository operationRepository;
    private List<Transaction> transactions;

    public FinancialPlanner(YearMonth yearMonth, OperationRepository operations, TransactionRepository transactionRepository){
        this.operationRepository = operations;
        this.transactionRepository = transactionRepository;
        this.transactions = this.transactionRepository.findByMonth(yearMonth);
        this.transactions.addAll(this.operationRepository.findAll().stream().map(o -> o.getTransactionFor(yearMonth)).collect(Collectors.toList()));
    }

    public List<Transaction> getTransactions(){
        return Collections.unmodifiableList(this.transactions);
    }




}
