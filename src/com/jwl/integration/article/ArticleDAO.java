package com.jwl.integration.article;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.entity.Article;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;

public class ArticleDAO extends BaseDAO implements IArticleDAO {

	@Override
	public ArticleId create(ArticleTO article) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		Article entity = ArticleConvertor.convertToEntity(article);
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			em.persist(entity);
			em.flush();
			if (localTrans) {
				ut.commit();
			}
		} catch (EntityExistsException e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DuplicateEntryException(e);
		} catch (Throwable e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
		return article.getId();
	}

	@Override
	public ArticleTO get(ArticleId id) throws DAOException {
		if (id == null) {
			return null;
		}
		ArticleTO article = null;
		EntityManager em = getEntityManager();
		try {
			Article entity = em.find(Article.class, id.getId());
			if (entity == null) {
				return null;
			}
			article = ArticleConvertor.convertFromEntity(entity);
		} catch (Throwable e) {
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
		return article;
	}

	@Override
	public void update(ArticleTO article) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		Article entity = ArticleConvertor.convertToEntity(article);
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}		
			em.joinTransaction();
			entity.setModified(new Date());
			em.merge(entity);
			em.flush();
			if (localTrans) {
				ut.commit();
			}
		} catch (Throwable e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}

	}

	@Override
	public void delete(ArticleId id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}			
			em.joinTransaction();
			Article article = em.find(Article.class, id.getId());
			em.remove(article);
			em.flush();
			if (localTrans) {
				ut.commit();
			}
		} catch (Throwable e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
	}

	@Override
	public List<ArticleTO> findAll() throws DAOException {
		List<ArticleTO> resultList = null;
		EntityManager em = getEntityManager();
		try {			
			Query query = em.createNamedQuery("Article.findAll");
			@SuppressWarnings("unchecked")
			List<Article> articles = query.getResultList();
			ArticleConvertor.convertList(articles);
		} catch (Throwable e) {
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
		return resultList;
	}

	@Override
	public List<ArticleTO> findEverywhere(String needle) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleTO getByTitle(String title) throws DAOException {
		if (title == null || title.isEmpty()) {
			return null;
		}
		ArticleTO result = null;
		EntityManager em = getEntityManager();
		try {		
			Query query = em.createNamedQuery("Article.findByTitle");
			query.setParameter(0, title);
			@SuppressWarnings("unchecked")
			List<Article> articles = query.getResultList();
			if (articles.isEmpty()) {
				return null;
			}
			result = ArticleConvertor.convertFromEntity(articles.get(0));
		} catch (Throwable e) {
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
		return result;
	}

	@Override
	public List<ArticleTO> findAll(int from, int maxCount) throws DAOException {
		List<ArticleTO> resultList = null;
		EntityManager em = getEntityManager();
		try {			
			Query query = em.createNamedQuery("Article.findAll");
			query.setFirstResult(from);
			query.setMaxResults(maxCount);
			@SuppressWarnings("unchecked")
			List<Article> articles = query.getResultList();
			resultList = ArticleConvertor.convertList(articles);
		} catch (Throwable e) {
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
		return resultList;
	}

	@Override
	public int getCount() throws DAOException {
		long count = 0;
		EntityManager em = getEntityManager();
		try {		
			Query query = em.createNamedQuery("Article.count");
			count = (Long) query.getSingleResult();
		} catch (Exception e) {
			throw new DAOException(e);
		}finally{
			closeEntityManager(em);
		}
		return (int) count;
	}
}
