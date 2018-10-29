package com.amhfilho.myfinance;

import com.amhfilho.myfinance.operation.OperationRepository;
import com.amhfilho.myfinance.transaction.Transaction;
import com.amhfilho.myfinance.transaction.TransactionRepository;
import com.amhfilho.myfinance.transaction.TransactionService;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialPlanner {

    private TransactionService transactionService;
    private OperationRepository operationRepository;
    private List<Transaction> transactions;

    public FinancialPlanner(YearMonth yearMonth, OperationRepository operations, TransactionService transactionService){
        this.transactionService = transactionService;
        this.operationRepository = operations;
        this.transactions = this.transactionService.findByMonth(yearMonth);
        this.transactions.addAll(this.operationRepository.findAll().stream().map(o -> o.getTransactionFor(yearMonth)).collect(Collectors.toList()));
    }

    public List<Transaction> getTransactions(){
        return Collections.unmodifiableList(this.transactions);
    }




}
