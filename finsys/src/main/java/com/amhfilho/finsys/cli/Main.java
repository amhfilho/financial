package com.amhfilho.finsys.cli;

import com.amhfilho.finsys.account.CheckingAccount;
import com.amhfilho.finsys.operation.DatabaseOperationRepository;
import com.amhfilho.finsys.persistence.PersistenceFactory;
import com.amhfilho.finsys.transaction.DatabaseTransactionRepository;
import com.amhfilho.finsys.transaction.Transaction;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = PersistenceFactory.getEntityManager();
        DatabaseOperationRepository operationRepository = new DatabaseOperationRepository(em);
        DatabaseTransactionRepository transactionRepository = new DatabaseTransactionRepository(em);

        CheckingAccount account = new CheckingAccount(operationRepository,transactionRepository);

        LocalDate now = LocalDate.now();
        List<Transaction> transactions =  account.getTransactions(YearMonth.of(now.getYear(),now.getMonth()));
        transactions.forEach(t -> System.out.println(t));




    }
}
