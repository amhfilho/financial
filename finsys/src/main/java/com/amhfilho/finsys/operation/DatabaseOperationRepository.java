package com.amhfilho.finsys.operation;

import javax.persistence.EntityManager;
import java.util.List;

public class DatabaseOperationRepository implements OperationRepository {

    private EntityManager em;

    public DatabaseOperationRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public List<Operation> findAll() {
        return em.createQuery("from Operation o").getResultList();
    }
}
