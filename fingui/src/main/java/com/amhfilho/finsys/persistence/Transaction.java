package com.amhfilho.finsys.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate transactionDate;
	
	private String description;
	
	private BigDecimal amount;
	
	private Integer actualInstallment;
	
	private Integer totalInstallment;
	
	private Status status;
	
	public String getInstallmentsDescription() {
		return actualInstallment + "/" + totalInstallment;
	}
	
	
	@ManyToOne
	private Operation operation;
	
	public static final String[] columnNames = {"Date","Description","Amount"};
	
	public Transaction() {}
	
	public Transaction(LocalDate transactionDate, String description, BigDecimal amount) {
		this.transactionDate = transactionDate;
		this.description = description;
		this.amount = amount;
	}
	
	public enum Status {
		PENDING, DONE, LATE;
	}
	
	
	
	

}
