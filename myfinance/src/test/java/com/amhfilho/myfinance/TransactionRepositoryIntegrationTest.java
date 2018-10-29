package com.amhfilho.myfinance;

import com.amhfilho.myfinance.transaction.Transaction;
import com.amhfilho.myfinance.transaction.TransactionRepository;
import com.amhfilho.myfinance.transaction.TransactionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TransactionRepository repository;

    @Test
    public void shouldCreateNewTransaction(){
        Transaction t = new Transaction("Test transaction",
                new BigDecimal("100.0"),
                LocalDate.of(2018, Month.OCTOBER,29),
                TransactionType.VARIABLE);
        repository.save(t);
        repository.flush();

        assertThat(t.getId()).isNotNull();

    }
}
