package com.amhfilho.finsys;

import com.amhfilho.finsys.transaction.RepeatOptions;
import com.amhfilho.finsys.transaction.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

public class TransactionForecastTest {
    @Test
    public void shouldReadTransactionsForMonthMarch(){
        YearMonth march2019 = YearMonth.of(2019,Month.MARCH);
        TransactionForecast forecast = new TransactionForecast(march2019);
        List<Transaction> transactionsForMarch = forecast.transactions();
    }

    public void shouldCreateTransactions(){
        Transaction rentPayment = Transaction.builder()
                                    .withDescription("Aluguel")
                                    .onDate(LocalDate.now())
                                    .repeatedFor(RepeatOptions.montly(12))
                                    .withAmount(new BigDecimal("1950.00"))
                                    .build();

    }
}
