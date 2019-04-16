package com.amhfilho.finsys.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


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

	@Enumerated(EnumType.ORDINAL)
	private Status status;

	@Transient
	private BigDecimal balance = new BigDecimal("0");
	
	public String getInstallmentsDescription() {
		if(totalInstallment == 0) return "-";
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
