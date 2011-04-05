package com.jwl.integration.topic;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.TopicConverter;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.Topic;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;

public class TopicDAO extends BaseDAO implements ITopicDAO {

	@Override
	public Integer create(TopicTO topic, ArticleId articleId) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		Topic entity = TopicConverter.convertToEntity(topic);
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Article article = em.find(Article.class, articleId.getId());
			entity.setArticle(article);
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
		} finally{
			closeEntityManager(em);
		}
		return entity.getId();
	}

	@Override
	public void delete(Integer id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Topic topic = em.find(Topic.class, id);
			em.remove(topic);
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
		} finally{
			closeEntityManager(em);
		}
	}

	@Override
	public void close(Integer id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Topic topic = em.find(Topic.class, id);
			topic.setClosed(true);
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
		} finally{
			closeEntityManager(em);
		}
	}

	@Override
	public void open(Integer id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Topic topic = em.find(Topic.class, id);
			topic.setClosed(false);
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
		} finally{
			closeEntityManager(em);
		}
		
	}

	@Override
	public TopicTO find(Integer id) throws DAOException {
		EntityManager em =getEntityManager();
		Topic topic = null;
		try{
			topic = em.find(Topic.class, id);
		}catch(Exception e){
			throw new DAOException(e);
		}
		return TopicConverter.convertFromEntity(topic);
	}

}
