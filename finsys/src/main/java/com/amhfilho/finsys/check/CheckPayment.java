package com.amhfilho.finsys.check;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CheckPayment {
    private Check check;
    private LocalDate paymentDate;

    public CheckPayment(Check check, LocalDate paymentDate) {
        this.check = check;
        this.paymentDate = paymentDate;
    }


}
