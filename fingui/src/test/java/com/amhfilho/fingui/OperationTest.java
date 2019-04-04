package com.amhfilho.fingui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import com.amhfilho.finsys.persistence.Operation;
import com.amhfilho.finsys.persistence.Transaction;

public class OperationTest {
	
	@Test
	public void shouldCreateTransactionForApr2019() {
		YearMonth ym = YearMonth.of(2019, Month.APRIL);
		LocalDate initialDate = LocalDate.of(2019, Month.APRIL, 10);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1")).installments(3);
		Transaction transaction = operation.createTransaction(ym);
		
		assertNotNull(transaction);
		assertTrue(transaction.getTransactionDate().isEqual(initialDate));
		assertEquals("Test", transaction.getDescription());
		assertEquals(new BigDecimal("1"), transaction.getAmount());
		assertEquals(new Integer(1), transaction.getActualInstallment());
	}
	
	@Test
	public void shouldCreate3Transactions() {
		LocalDate initialDate = LocalDate.of(2019, Month.APRIL, 10);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1")).installments(3);
		
		YearMonth apr2019 = YearMonth.of(2019, Month.APRIL);
		YearMonth may2019 = YearMonth.of(2019, Month.MAY);
		YearMonth jun2019 = YearMonth.of(2019, Month.JUNE);
		
		Transaction t1 = operation.createTransaction(apr2019);
		assertEquals(new Integer(1), t1.getActualInstallment());
		assertEquals(initialDate, t1.getTransactionDate());
		
		Transaction t2 = operation.createTransaction(may2019);
		assertEquals(new Integer(2), t2.getActualInstallment());
		assertEquals(LocalDate.of(2019, Month.MAY, 10), t2.getTransactionDate());
		
		Transaction t3 = operation.createTransaction(jun2019);
		assertEquals(new Integer(3), t3.getActualInstallment());
		assertEquals(LocalDate.of(2019, Month.JUNE, 10), t3.getTransactionDate());
	}
	
	@Test
	public void shouldCreate3TransactionsFromLastYear() {
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 30);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1")).installments(3);
		
		YearMonth dec2018 = YearMonth.of(2018, Month.DECEMBER);
		YearMonth jan2019 = YearMonth.of(2019, Month.JANUARY);
		YearMonth feb2019 = YearMonth.of(2019, Month.FEBRUARY);
		
		Transaction t1 = operation.createTransaction(dec2018);
		assertEquals(new Integer(1), t1.getActualInstallment());
		assertEquals(initialDate, t1.getTransactionDate());
		
		Transaction t2 = operation.createTransaction(jan2019);
		assertEquals(new Integer(2), t2.getActualInstallment());
		assertEquals(LocalDate.of(2019, Month.JANUARY, 30), t2.getTransactionDate());
		
		Transaction t3 = operation.createTransaction(feb2019);
		assertEquals(new Integer(3), t3.getActualInstallment());
		assertEquals(LocalDate.of(2019, Month.FEBRUARY, 28), t3.getTransactionDate());
		
	}
	
	@Test
	public void shouldCreateTransactionForUndefinedOperation() {
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 31);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1"));
		
		YearMonth apr2019 = YearMonth.of(2019, Month.APRIL);
		Transaction t = operation.createTransaction(apr2019);
		
		assertNotNull(t);
		assertEquals(LocalDate.of(2019, Month.APRIL, 30), t.getTransactionDate());
	}
	
	@Test
	public void shouldNotCreateTransaction() {
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 31);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1")).installments(2);
		YearMonth apr2019 = YearMonth.of(2019, Month.APRIL);
		Transaction t = operation.createTransaction(apr2019);
		assertNull(t);
		
	}
	
	@Test
	public void shouldHaveTransactionBetweenNov2018AndMay2019() {
		YearMonth ym = YearMonth.of(2019, Month.APRIL);
		LocalDate initialDate = LocalDate.of(2018, Month.NOVEMBER, 2);
		Operation operation= Operation.of(initialDate, "Test", new BigDecimal("1")).installments(7);
		assertTrue(operation.hasTransactionFor(ym));
	}
	
	@Test
	public void shouldHaveTransactionBetweenApr2019And3Installments() {
		YearMonth ym = YearMonth.of(2019, Month.APRIL);
		LocalDate initialDate = LocalDate.of(2019, Month.APRIL, 10);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1")).installments(3);
		assertTrue(operation.hasTransactionFor(ym));
	}
	
	@Test
	public void shouldHaveTransactionForUndefinedOperation() {
		YearMonth ym = YearMonth.of(2019, Month.APRIL);
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 25);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1"));
		assertTrue(operation.hasTransactionFor(ym));
	}
	
	@Test
	public void shouldNotHaveTransaction() {
		YearMonth ym = YearMonth.of(2019, Month.APRIL);
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 25);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1")).installments(2);
		assertFalse(operation.hasTransactionFor(ym));
	}
	
	@Test
	public void shouldCreateValidFebruaryDate() {
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 25);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1"));
		
		LocalDate date = operation.createDate(2019, Month.FEBRUARY, 31);
		assertNotNull(date);
		assertEquals(28, date.getDayOfMonth());
	}
	
	@Test
	public void shouldCreateValidAprilDate() {
		LocalDate initialDate = LocalDate.of(2018, Month.DECEMBER, 25);
		Operation operation = Operation.of(initialDate, "Test", new BigDecimal("1"));
		
		LocalDate date = operation.createDate(2019, Month.APRIL, 31);
		assertNotNull(date);
		assertEquals(30, date.getDayOfMonth());
	}
	
}
