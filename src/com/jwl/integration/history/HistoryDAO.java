package com.jwl.integration.history;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.integration.ConnectionFactory;
import com.jwl.integration.convertor.HistoryConvertor;
import com.jwl.integration.entity.History;
import com.jwl.integration.entity.HistoryPK;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Lukas Rychtecky
 */
public class HistoryDAO implements IHistoryDAO {

	private static final String DELETE_BY_ARTICLE_ID = "History.deleteByArticleId";
	private static final String DELETE_BY_ARTICLE_ID_AND_YOUNGER_THAN = "History.deleteByArticleIdAndYoungerThan";
	private static final String FIND_BY_ARTICLE_ID = "History.findByArticleId";
	private EntityManagerFactory factory;
	private EntityManager manager;

	public HistoryDAO() {
		this.factory = ConnectionFactory.getInstance().getConnection();
	}

	@Override
	public void create(HistoryTO history) throws DAOException {
		try {
			this.manager = this.factory.createEntityManager();
			History entity = HistoryConvertor.convertToEntity(history);
			entity.setModified(new Date());
			this.manager.persist(entity);
			this.manager.flush();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public HistoryTO get(HistoryId id) throws DAOException {
		HistoryTO history = null;
		try {
			this.manager = this.factory.createEntityManager();
			History entity = this.manager.find(History.class, new HistoryPK(id.getId(), id.getArticleId().getId()));
			history = HistoryConvertor.convertFromEntity(entity);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return history;
	}

	@Override
	public void update(HistoryTO history) throws DAOException {
		try {
			this.manager = this.factory.createEntityManager();
			History entity = HistoryConvertor.convertToEntity(history);
			entity.setModified(new Date());
			this.manager.merge(entity);
			this.manager.flush();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(HistoryId id) throws DAOException {
		try {
			this.manager = this.factory.createEntityManager();
			History history = this.manager.find(History.class, id);
			if (history == null) {
				throw new EntityNotFoundException("Can't delete history id: " + id);
			}

			this.manager.remove(this.manager.merge(history));
			this.manager.flush();
		} catch (EntityNotFoundException e) {

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void deleteAll(ArticleId id) throws DAOException {
		try {
			this.manager = this.factory.createEntityManager();
			Query query = this.manager.createNamedQuery(HistoryDAO.DELETE_BY_ARTICLE_ID);
			query.setParameter("articleId", id.getId());
			query.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<HistoryTO> findAll(ArticleId id) throws DAOException {
		List<HistoryTO> histories = new ArrayList<HistoryTO>();
		try {
			this.manager = this.factory.createEntityManager();
			Query query = this.manager.createNamedQuery(HistoryDAO.FIND_BY_ARTICLE_ID);
			query.setParameter("articleId", id.getId());
			@SuppressWarnings("unchecked")
			List<History> result = query.getResultList();
			for (History history : result) {
				histories.add(HistoryConvertor.convertFromEntity(history));
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return histories;
	}

	@Override
	public void deleteAllYoungerThan(ArticleId id, Date date) throws DAOException {
		try {
			this.manager = this.factory.createEntityManager();
			Query query = this.manager.createNamedQuery(HistoryDAO.DELETE_BY_ARTICLE_ID_AND_YOUNGER_THAN);
			query.setParameter("articleId", id.getId());
			query.setParameter("currentDate", date);
			query.executeUpdate();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
