package com.amhfilho.myfinance.transaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TransactionRepository repository;

    @Before
    public void setup(){
        Transaction t1 = new Transaction("Test transaction 1",
                new BigDecimal("100.0"),
                LocalDate.of(2018, Month.OCTOBER,30),
                TransactionType.VARIABLE);

        Transaction t2 = new Transaction("Test transaction 2",
                new BigDecimal("200.0"),
                LocalDate.of(2018, Month.OCTOBER,31),
                TransactionType.FIXED);

        testEntityManager.persist(t1);
        testEntityManager.persist(t2);
        testEntityManager.flush();
    }

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

    @Test
    public void shouldQueryForCreatedTransactions(){
        List<Transaction> list = repository.findAll();
        assertThat(list.size()).isEqualTo(2);
    }
}
