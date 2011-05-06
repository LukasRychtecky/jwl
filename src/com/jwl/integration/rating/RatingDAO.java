package com.jwl.integration.rating;

import javax.persistence.EntityManager;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.RatingTO;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.RatingConvertor;
import com.jwl.integration.entity.Rating;
import com.jwl.integration.entity.RatingPK;
import com.jwl.integration.exceptions.DAOException;

public class RatingDAO extends BaseDAO implements IRatingDAO {

	@Override
	public void create(RatingTO rating, ArticleId articleId)
			throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			Rating entity = RatingConvertor.convertToEntity(rating, articleId);
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
		} catch (Throwable e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
	}

	@Override
	public void update(RatingTO rating, ArticleId articleId)
			throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			Rating entity = RatingConvertor.convertToEntity(rating, articleId);
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
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
		} finally {
			closeEntityManager(em);
		}
	}

	@Override
	public RatingTO find(ArticleId articleId, String author)
			throws DAOException {
		RatingPK key = new RatingPK(articleId.getId(), author);
		EntityManager em = getEntityManager();
		RatingTO rating = null;
		try {
			Rating entity = em.find(Rating.class, key);
			if (entity == null) {
				return null;
			}
			rating = RatingConvertor.convertFromEntity(entity);
		} catch (Throwable t) {
			throw new DAOException(t);
		} finally {
			closeEntityManager(em);
		}
		return rating;
	}
}
