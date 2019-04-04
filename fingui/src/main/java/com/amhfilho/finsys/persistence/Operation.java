package com.amhfilho.finsys.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.amhfilho.finsys.persistence.Transaction.Status;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Operation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate initialDate;
	
	private String description;
	
	private BigDecimal amount;
	
	private Integer installments;
	
	private String payCheckNumber;
	
	private LocalDate payCheckDoneDate;
	
	private OperationStatus status;
	
	private String category;
	
	public boolean isUndefined() {
		return installments == null || installments == 0;
	}
	
	public Transaction createTransaction(YearMonth yearMonth) {
		if(hasTransactionFor(yearMonth)) {
			Transaction t = new Transaction();
			t.setDescription(description);
			t.setOperation(this);
			t.setAmount(amount);
			t.setStatus(Status.PENDING);
			t.setTransactionDate(createDate(yearMonth.getYear(), 
											  yearMonth.getMonth(), 
											  initialDate.getDayOfMonth()));
			t.setTotalInstallment(installments);
			t.setActualInstallment(getActualInstallment(yearMonth));
			return t;
		}

		return null;
	}
	
	public LocalDate createDate(int year, Month month, int day) {
		LocalDate date = LocalDate.of(year, month, 1);
		if(date.lengthOfMonth() < day) {
			day = date.lengthOfMonth() ; 
		}
		return date.withDayOfMonth(day);
	}
	
	private int getActualInstallment(YearMonth ym) {
		if(isUndefined()) return 0;
		Period diff = Period.between(initialDate.withDayOfMonth(1),
									 LocalDate.of(ym.getYear(), ym.getMonth(), 1));
		
		return diff.getMonths() + 1;
	}
	
	public LocalDate getFinalDate() {
		return initialDate.plusMonths(installments -1);
	}
	
	public boolean hasTransactionFor(YearMonth ym) {
		if(!isUndefined()) {
			LocalDate startDate = initialDate.withDayOfMonth(1); 
			LocalDate finalDate = getFinalDate();
			finalDate = finalDate.withDayOfMonth(finalDate.lengthOfMonth());
			LocalDate actualDate = LocalDate.of(ym.getYear(), ym.getMonth(), 15);
			return (actualDate.isEqual(startDate) || actualDate.isAfter(startDate)) &&
				   (actualDate.isEqual(finalDate) || actualDate.isBefore(finalDate));
		}
		else return true;	
	}
	
	
	public static Operation of(LocalDate initialDate, String description, BigDecimal amount) {
		Operation op = new Operation();
		op.setAmount(amount);
		op.setDescription(description);
		op.setInitialDate(initialDate);
		return op;
	}
	
	public Operation installments(Integer installments) {
		this.installments = installments;
		return this;
	}
	

	@Override
	public String toString() {
		return "Operation [id=" + id + ", initialDate=" + initialDate + ", description=" + description + ", amount="
				+ amount + ", installments=" + installments + ", payCheckNumber=" + payCheckNumber
				+ ", payCheckDoneDate=" + payCheckDoneDate + ", status=" + status + ", category=" + category + "]";
	}

	
	
	

}
