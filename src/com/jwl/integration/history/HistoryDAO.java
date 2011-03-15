package com.jwl.integration.history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.HistoryConvertor;
import com.jwl.integration.entity.History;
import com.jwl.integration.entity.HistoryPK;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.EntityNotFoundException;

/**
 * 
 * @author Lukas Rychtecky
 */
public class HistoryDAO extends BaseDAO implements IHistoryDAO {

	private static final String DELETE_BY_ARTICLE_ID = "History.deleteByArticleId";
	private static final String DELETE_BY_ARTICLE_ID_AND_YOUNGER_THAN = "History.deleteByArticleIdAndYoungerThan";
	private static final String FIND_BY_ARTICLE_ID = "History.findByArticleId";

	@Override
	public void create(HistoryTO history) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			History entity = HistoryConvertor.convertToEntity(history);
			entity.setModified(new Date());
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
	public HistoryTO get(HistoryId id) throws DAOException {
		HistoryTO history = null;
		EntityManager em = getEntityManager();
		try {
			History entity = em.find(History.class, new HistoryPK(id.getId(),
					id.getArticleId().getId()));
			history = HistoryConvertor.convertFromEntity(entity);
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
		return history;
	}

	@Override
	public void update(HistoryTO history) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			History entity = HistoryConvertor.convertToEntity(history);
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
		} finally {
			closeEntityManager(em);
		}
	}

	@Override
	public void delete(HistoryId id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			History history = em.find(History.class, id);
			if (history == null) {
				throw new EntityNotFoundException("Can't delete history id: "
						+ id);
			}

			em.remove(em.merge(history));
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
	public void deleteAll(ArticleId id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Query query = em.createNamedQuery(HistoryDAO.DELETE_BY_ARTICLE_ID);
			query.setParameter("articleId", id.getId());
			query.executeUpdate();
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
	public List<HistoryTO> findAll(ArticleId id) throws DAOException {
		List<HistoryTO> histories = new ArrayList<HistoryTO>();
		EntityManager em = getEntityManager();
		try {
			Query query = em.createNamedQuery(HistoryDAO.FIND_BY_ARTICLE_ID);
			query.setParameter("articleId", id.getId());
			@SuppressWarnings("unchecked")
			List<History> result = query.getResultList();
			for (History history : result) {
				histories.add(HistoryConvertor.convertFromEntity(history));
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
		return histories;
	}

	@Override
	public void deleteAllYoungerThan(ArticleId id, Date date)
			throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			Query query = em
					.createNamedQuery(HistoryDAO.DELETE_BY_ARTICLE_ID_AND_YOUNGER_THAN);
			query.setParameter("articleId", id.getId());
			query.setParameter("currentDate", date);
			query.executeUpdate();
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

}
