package com.amhfilho.finsys.check;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
public class FinancialCheckPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private FinancialCheck financialCheck;
    private LocalDate paymentDate;

    public FinancialCheckPayment(FinancialCheck financialCheck, LocalDate paymentDate) {
        this.financialCheck = financialCheck;
        this.paymentDate = paymentDate;
    }


}
