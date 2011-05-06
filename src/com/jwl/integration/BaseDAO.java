package com.jwl.integration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import com.jwl.integration.exceptions.DAOException;

public class BaseDAO {

	private Transaction suspendedTransaction = null;

	protected EntityManager getEntityManager() throws DAOException{
		suspendTransaction();
		EntityManagerFactory emf = ConnectionFactory.getInstance()
				.getConnection();
		EntityManager em = emf.createEntityManager();		
		return em;

	}

	protected UserTransaction getUserTransaction(){
		UserTransaction ut = null;
		try{
			InitialContext context = new InitialContext();
			ut = (UserTransaction) context.lookup("java:comp/UserTransaction");

		}catch(NamingException e){
			e.printStackTrace();
		}
		return ut;
	}

	private TransactionManager getTransactionManager() throws DAOException{
		TransactionManager manager = null;
		try{
			InitialContext context = new InitialContext();
			manager = (TransactionManager) context
					.lookup("java:/TransactionManager");
		}catch(NamingException e){
			throw new DAOException("Transaction manager lookup failed");
		}
		return manager;
	}

 /*	protected void beginTransaction() throws DAOException{
		TransactionManager manager = getTransactionManager();
		try{
			manager.begin();
		}catch(Exception e){
			throw new DAOException("Transaction begin failed.");
		}
	}

	protected void commitTransaction() throws DAOException{
		TransactionManager manager = getTransactionManager();
		try{
			manager.commit();			
		}catch(Exception e){
			throw new DAOException("Transaction commit failed.");
		}
	}
	
	protected void rollbackTransaction() throws DAOException{
		TransactionManager manager = getTransactionManager();
		try{
			manager.rollback();			
		}catch(Exception e){
			throw new DAOException("Transaction rollback failed.");
		}
	} */

	protected void closeEntityManager(EntityManager em) throws DAOException{
		if(em != null && em.isOpen()){
			em.close();
		}
		resumeTransaction();
	}
	
	private void suspendTransaction() throws DAOException{
		TransactionManager manager = getTransactionManager();
		try{
			suspendedTransaction = manager.suspend();
		}catch(Exception e){
			throw new DAOException("Suspending transaction failed.");
		}
	}
	
	private void resumeTransaction() throws DAOException{
		TransactionManager manager = getTransactionManager();
		try{
			if(suspendedTransaction != null){
				manager.resume(suspendedTransaction);
				suspendedTransaction = null;
			}			
		}catch(Exception e){
			throw new DAOException("Suspending transaction failed.");
		}
	}
}
