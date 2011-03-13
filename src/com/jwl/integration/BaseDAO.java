package com.jwl.integration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

public class BaseDAO {

	protected EntityManager getEntityManager() {
		EntityManagerFactory emf = ConnectionFactory.getInstance().getConnection();
		EntityManager em = emf.createEntityManager();
		return em;

	}

	protected UserTransaction getUserTransaction() {
		UserTransaction ut = null;
		try {
			InitialContext context = new InitialContext();
			ut = (UserTransaction) context.lookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ut;
	}

	protected void closeEntityManager(EntityManager em) {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
}
