package com.amhfilho.myfinance;

import com.amhfilho.myfinance.transaction.Transaction;
import com.amhfilho.myfinance.transaction.TransactionType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    Transaction transaction1 = new Transaction("Compra 1", new BigDecimal("-200"), LocalDate.of(2018, Month.OCTOBER,23), TransactionType.VARIABLE);
    Transaction transaction2 = new Transaction("Compra 3", new BigDecimal("-150"), LocalDate.of(2018, Month.OCTOBER,23), TransactionType.VARIABLE);
    Transaction transaction3 = new Transaction("Compra 2", new BigDecimal("-300"), LocalDate.of(2018, Month.OCTOBER,22), TransactionType.VARIABLE);
    Transaction transaction4 = new Transaction("Salário", new BigDecimal("600"), LocalDate.of(2018, Month.OCTOBER,25), TransactionType.VARIABLE);

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenGetBalanceOnNullDate(){
        CheckingAccount account = new CheckingAccount("My Account");
        account.getBalanceOn(null);
    }

    @Test
    public void shouldReturnBalanceInFuture(){
        CheckingAccount account = new CheckingAccount(
                "My Account",
                new BigDecimal("100"),
                Arrays.asList(transaction1, transaction3, transaction2, transaction4));

        BigDecimal givenBalance = account.getBalanceOn(LocalDate.of(2018,Month.OCTOBER,25));
        BigDecimal expected = new BigDecimal("350.0");

        assertEquals(expected,givenBalance);
    }

    @Test
    public void shouldReturnBalanceNow(){
        CheckingAccount account = new CheckingAccount(
                "My Account",
                new BigDecimal("100"),
                Arrays.asList(transaction1, transaction3, transaction2, transaction4));
        BigDecimal givenBalance = account.getBalanceOn(LocalDate.now());
        assertEquals(new BigDecimal("-250.0"), givenBalance);
    }

    @Test
    public void shouldReturnBalanceInPast(){
        CheckingAccount account = new CheckingAccount(
                "My Account",
                new BigDecimal("100"),
                Arrays.asList(transaction1, transaction3, transaction2, transaction4));

        BigDecimal givenBalance = account.getBalanceOn(LocalDate.of(2018,Month.OCTOBER,22));
        BigDecimal expected = new BigDecimal("100");

        assertEquals(expected,givenBalance);
    }
}
