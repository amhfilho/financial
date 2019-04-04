package com.amhfilho.finsys.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
	private static EntityManagerFactory emf;
	
	static {
		emf = Persistence.createEntityManagerFactory("PERSISTENCE");
	}
	
	private static EntityManager em;
	
	public static EntityManager getEntityManager() {
		if(em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}
		return em;
	}
	
	
	
}
