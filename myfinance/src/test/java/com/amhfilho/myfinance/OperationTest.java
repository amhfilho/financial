package com.amhfilho.myfinance;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OperationTest {

    @Test
    public void createValidTransaction(){
        Operation operation = new Operation("Rental", new BigDecimal("2000.0"), 30);
        Transaction transaction = operation.getTransactionFor(YearMonth.of(2018, Month.OCTOBER));

        assertNotNull(transaction);
        assertTrue(transaction.getDate().isEqual(LocalDate.of(2018, Month.OCTOBER, 30)));
        assertEquals(new BigDecimal("2000.0"), transaction.getValue());
        assertEquals("Rental", transaction.getDescription());

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOperationTestForNullDescription(){
        new Operation(null,new BigDecimal("1"),1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOperationTestInvalidDueDate(){
        new Operation("Test",new BigDecimal("1"),32);
    }
}
