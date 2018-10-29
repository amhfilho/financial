package com.amhfilho.myfinance;

import com.amhfilho.myfinance.transaction.Transaction;

import java.util.List;

public class TransactionRepositoryTestImpl extends TransactionRepositoryAdapter {

    private List<Transaction> transactions;

    public TransactionRepositoryTestImpl(List<Transaction> transactions){
        this.transactions = transactions;
    }

    @Override
    public List<Transaction> findAll() {
        return this.transactions;
    }
}
