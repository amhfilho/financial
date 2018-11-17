package com.amhfilho.finsys;

import com.amhfilho.finsys.transaction.DatabaseTransactionRepository;
import com.amhfilho.finsys.transaction.Transaction;
import com.amhfilho.finsys.transaction.TransactionType;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionDatabaseTest {
    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setupDatabase(){
        emf = DatabaseSetupTest.getEntityManagerFactory();
    }
    @AfterClass
    public static void shutdown(){
        if(emf != null){
            emf.close();
        }
    }


    @Before
    public void setupData(){
        Transaction t1 = new Transaction("Compra",new BigDecimal("100.0"), LocalDate.of(2018, Month.NOVEMBER,2), TransactionType.VARIABLE);
        Transaction t2 = new Transaction("Conta",new BigDecimal("250.0"), LocalDate.of(2018, Month.NOVEMBER,1), TransactionType.VARIABLE);
        Transaction t3 = new Transaction("Compra2",new BigDecimal("300.0"), LocalDate.of(2018, Month.NOVEMBER,3), TransactionType.VARIABLE);
        Transaction t4 = new Transaction("Conta2",new BigDecimal("150.0"), LocalDate.of(2018, Month.NOVEMBER,4), TransactionType.VARIABLE);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.persist(t4);

        em.getTransaction().commit();

        em.close();
    }

    @After
    public void cleanData(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("delete from transaction").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findAllTransactionTest(){
        DatabaseTransactionRepository repository = new DatabaseTransactionRepository(emf.createEntityManager());
        List<Transaction> allTransactions = repository.findAll();
        assertEquals(4, allTransactions.size());
    }
}
