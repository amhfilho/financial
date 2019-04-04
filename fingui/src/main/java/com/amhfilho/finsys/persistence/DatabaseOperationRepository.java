package com.amhfilho.finsys.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import com.amhfilho.finsys.gui.operation.OperationRepository;

public class DatabaseOperationRepository implements OperationRepository {
	
	private EntityManager em;
	
	public DatabaseOperationRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Operation> findAll() {
		return em.createQuery("select o from Operation o order by o.initialDate", Operation.class).getResultList();
	}

	@Override
	public void save(Operation operation) {
		this.em.getTransaction().begin();
		if(operation.getId() == null) {
			this.em.persist(operation);
		} else {
			this.em.merge(operation);
		}
		this.em.getTransaction().commit();
		
	}

	@Override
	public void delete(Operation operation) {
		this.em.getTransaction().begin();
		this.em.remove(operation);
		this.em.getTransaction().commit();
	}

}
