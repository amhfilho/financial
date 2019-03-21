package com.amhfilho.finsys.check;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Check {
    private String bank;
    private String number;
    private BigDecimal amount;
    private LocalDate dueDate;

    public Check(String number, BigDecimal amount, LocalDate dueDate) {
        this.number = number;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public Check(String bank, String number, BigDecimal amount, LocalDate dueDate) {
        this.bank = bank;
        this.number = number;
        this.amount = amount;
        this.dueDate = dueDate;
    }


}
