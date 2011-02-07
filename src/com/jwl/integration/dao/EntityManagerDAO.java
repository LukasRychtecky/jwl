package com.jwl.integration.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.jwl.integration.ConnectionFactory;

@Stateless
public class EntityManagerDAO implements Serializable {

	private static final long serialVersionUID = -8198800235309610794L;
	private EntityManager em;
	private EntityManagerFactory connection;

	public EntityManagerDAO() {
		connection = ConnectionFactory.getInstance().getConnection();
	}

	public Object updateObject(Object object) {
		em = connection.createEntityManager();		
		Object obj = em.merge(object);
		em.flush();
		return obj;
	}

	public void createObject(Object object) {
		em = connection.createEntityManager();
		em.persist(object);
		em.flush();
	}

	public void refresh(Object object) {
		em = connection.createEntityManager();
		em.refresh(object);
	}

	public <T> T find(Class<T> clazz, Integer id) {
		em = connection.createEntityManager();
		return em.find(clazz, id);
	}

	public void deleteObject(Object object) {
		em = connection.createEntityManager();
		em.remove(em.merge(object));
		em.flush();
	}

	public List<?> doNamedQuery(String name, Object[] params) {
		em = connection.createEntityManager();
		Query q = em.createNamedQuery(name);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i, params[i]);
		}
		return q.getResultList();
	}

	protected EntityManagerFactory getEntityManagerFactory() {
		return this.connection;
	}



}
