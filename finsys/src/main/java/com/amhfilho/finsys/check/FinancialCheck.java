package com.amhfilho.finsys.check;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class FinancialCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bank;
    private String number;
    private BigDecimal amount;
    private LocalDate dueDate;
    @OneToOne
    private FinancialCheckPayment financialCheckPayment;


    protected FinancialCheck(){}

    public FinancialCheck(String number, BigDecimal amount, LocalDate dueDate) {
        this.number = number;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public FinancialCheck(String bank, String number, BigDecimal amount, LocalDate dueDate) {
        this.bank = bank;
        this.number = number;
        this.amount = amount;
        this.dueDate = dueDate;
    }


}
