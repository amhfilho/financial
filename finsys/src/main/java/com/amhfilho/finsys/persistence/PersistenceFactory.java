package com.amhfilho.finsys.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceFactory {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    static {
        emf = Persistence.createEntityManagerFactory("PERSISTENCE");
    }

    public static EntityManager getEntityManager(){
        if(em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public static void close(){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }


}
