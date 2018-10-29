package com.amhfilho.myfinance.operation;

import com.amhfilho.myfinance.transaction.Transaction;
import com.amhfilho.myfinance.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Financial operation, that could result in one or more Transaction
 */
public class Operation {

    private Long id;
    private String description;
    private BigDecimal value;
    private Integer dueDate;

    public Operation(String description, BigDecimal value, Integer dueDate) {
        if(description == null || value == null || dueDate == null || dueDate < 1 || dueDate > 31){
            throw new IllegalArgumentException("Invalid parameters. Neither description, or value must not be null. Due date must be " +
                    "between 1 and 31");
        }
        this.description = description;
        this.value = value;
        this.dueDate = dueDate;
    }

    public Transaction getTransactionFor(YearMonth yearMonth) {
        LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), getDueDate());
        return new Transaction(getDescription(), getValue(), date, TransactionType.FIXED);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", dueDate=" + dueDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getDueDate() {
        return dueDate;
    }
}
