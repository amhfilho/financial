package com.amhfilho.myfinance;

import com.amhfilho.myfinance.operation.Operation;
import com.amhfilho.myfinance.operation.OperationRepository;
import com.amhfilho.myfinance.operation.OperationRepositoryTestImpl;
import com.amhfilho.myfinance.transaction.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class FinancialPlannerTest {
    @Test
    public void shouldReturnValidTransactionsForOctober(){
        //Given 2 Operations
        Operation rent = new Operation("House Rent", new BigDecimal("2000.0"),30);
        Operation condo = new Operation("Condominium Fee", new BigDecimal("1800.0"),11);

        //Given 2 transactions
        Transaction purchase1 = new Transaction("Purchase 1", new BigDecimal("200.0"), LocalDate.of(2018, Month.OCTOBER,24), TransactionType.VARIABLE);
        Transaction purchase2 = new Transaction("Purchase 2", new BigDecimal("365.0"), LocalDate.of(2018, Month.OCTOBER,13), TransactionType.VARIABLE);
        Transaction purchase3 = new Transaction("Purchase 3", new BigDecimal("400.0"), LocalDate.of(2018, Month.NOVEMBER,2), TransactionType.VARIABLE);

        //given FinancialPlannerTest
        TransactionRepository transactionRepository = new TransactionRepositoryTestImpl(Arrays.asList(purchase1,purchase2,purchase3));
        TransactionService transactionService = new TransactionServiceTestImpl(transactionRepository);
        OperationRepository operationRepository = new OperationRepositoryTestImpl(Arrays.asList(rent,condo));
        FinancialPlanner financialPlanner = new FinancialPlanner(YearMonth.of(2018, Month.OCTOBER), operationRepository, transactionService);

        //when query for transactions
        List<Transaction> transactions = financialPlanner.getTransactions();

        assertEquals(4, transactions.size());

        //sort by date
        List<Transaction> sorted = transactions.stream().collect(Collectors.toList());
        Collections.sort(sorted);

        assertEquals(LocalDate.of(2018,Month.OCTOBER,11), sorted.get(0).getDate());
        assertEquals(new BigDecimal("1800.0") , sorted.get(0).getValue());

        assertEquals(LocalDate.of(2018,Month.OCTOBER,13), sorted.get(1).getDate());
        assertEquals(new BigDecimal("365.0") , sorted.get(1).getValue());

        assertEquals(LocalDate.of(2018,Month.OCTOBER,24), sorted.get(2).getDate());
        assertEquals(new BigDecimal("200.0") , sorted.get(2).getValue());

        assertEquals(LocalDate.of(2018,Month.OCTOBER,30), sorted.get(3).getDate());
        assertEquals(new BigDecimal("2000.0") , sorted.get(3).getValue());

    }
}
