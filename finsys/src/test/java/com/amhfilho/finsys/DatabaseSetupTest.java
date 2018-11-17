package com.amhfilho.finsys;

import com.amhfilho.finsys.transaction.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class DatabaseSetupTest {
    private static EntityManagerFactory emf;

    @BeforeClass
    public static void createEntityManagerFactory(){
        emf  = Persistence.createEntityManagerFactory("PERSISTENCE");
    }

    @AfterClass
    public static void shutdown(){
        if(emf != null){
            emf.close();
        }
    }

    @Test
    public void createDatabaseTest(){
        EntityManager em = emf.createEntityManager();
        String sql = "select * from transaction";
        List<Transaction> result =  em.createNativeQuery(sql).getResultList();
        assertNotNull(result);
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        if(emf == null){
            createEntityManagerFactory();
        }
        return emf;
    }
}
