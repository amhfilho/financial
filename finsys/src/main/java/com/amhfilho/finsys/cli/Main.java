package com.amhfilho.finsys.cli;

import com.amhfilho.finsys.account.CheckingAccount;
import com.amhfilho.finsys.operation.DatabaseOperationRepository;
import com.amhfilho.finsys.persistence.PersistenceFactory;
import com.amhfilho.finsys.transaction.DatabaseTransactionRepository;
import com.amhfilho.finsys.transaction.Transaction;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) {
        startDatabaseConnection();

        DatabaseOperationRepository operationRepository = new DatabaseOperationRepository(em);
        DatabaseTransactionRepository transactionRepository = new DatabaseTransactionRepository(em);

        CheckingAccount account = new CheckingAccount(operationRepository,transactionRepository);

        LocalDate now = LocalDate.now();
        List<Transaction> transactions =  account.getTransactions(YearMonth.of(now.getYear(),now.getMonth()));
        transactions.forEach(t -> System.out.println(t));

        System.out.println("Type a command:");
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append(sc.nextLine());

        String[] words =  sb.toString().split("\\s+");
        System.out.println(Arrays.toString(words));

        switch(words[0]){
            case "insert":
                switch(words[1]){
                    case "transaction":

                }
        }

        closeDatabaseConnection();
    }

    public static void startDatabaseConnection(){
        em = PersistenceFactory.getEntityManager();
    }

    public static void closeDatabaseConnection(){
        em.close();
        PersistenceFactory.close();
    }
}
