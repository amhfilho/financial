package com.amhfilho.myfinance;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    Transaction compra1 = new Transaction("Compra 1", new BigDecimal("-200"), LocalDate.of(2018, Month.OCTOBER,23));
    Transaction compra3 = new Transaction("Compra 3", new BigDecimal("-150"), LocalDate.of(2018, Month.OCTOBER,23));
    Transaction compra2 = new Transaction("Compra 2", new BigDecimal("-300"), LocalDate.of(2018, Month.OCTOBER,22));
    Transaction salario = new Transaction("Salário", new BigDecimal("600"), LocalDate.of(2018, Month.OCTOBER,25));

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
                Arrays.asList(compra1,compra2,compra3,salario));

        BigDecimal givenBalance = account.getBalanceOn(LocalDate.of(2018,Month.OCTOBER,25));
        BigDecimal expected = new BigDecimal("350.0");

        assertEquals(expected,givenBalance);
    }

    @Test
    public void shouldReturnBalanceNow(){
        CheckingAccount account = new CheckingAccount(
                "My Account",
                new BigDecimal("100"),
                Arrays.asList(compra1,compra2,compra3,salario));
        BigDecimal givenBalance = account.getBalanceOn(LocalDate.now());
        assertEquals(new BigDecimal("-250.0"), givenBalance);
    }

    @Test
    public void shouldReturnBalanceInPast(){
        CheckingAccount account = new CheckingAccount(
                "My Account",
                new BigDecimal("100"),
                Arrays.asList(compra1,compra2,compra3,salario));

        BigDecimal givenBalance = account.getBalanceOn(LocalDate.of(2018,Month.OCTOBER,22));
        BigDecimal expected = new BigDecimal("100");

        assertEquals(expected,givenBalance);
    }
}
