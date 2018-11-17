package com.amhfilho.finsys.account;

import com.amhfilho.finsys.operation.Operation;
import com.amhfilho.finsys.operation.OperationRepository;
import com.amhfilho.finsys.transaction.Transaction;
import com.amhfilho.finsys.transaction.TransactionRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class CheckingAccount {

    private OperationRepository operationRepository;
    private TransactionRepository transactionRepository;


    public CheckingAccount(OperationRepository operationRepository, TransactionRepository transactionRepository) {
        this.operationRepository = operationRepository;
        this.transactionRepository = transactionRepository;
    }


    public List<Transaction> getTransactions(YearMonth yearMonth){
        List<Transaction> transactions = transactionRepository.findAll();
        List<Operation> operations = operationRepository.findAll();

        
        transactions.addAll(operations.stream().map(o->o.getTransactionFor(yearMonth)).collect(Collectors.toList()));

        return transactions;
    }
}
