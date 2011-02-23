package com.jwl.integration.dao;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.ConnectionFactory;
import com.jwl.integration.cache.ArticleHome;
import com.jwl.business.article.ArticleId;
import com.jwl.integration.cache.ArticleListHandler;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.entity.Article;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;
import com.jwl.integration.exceptions.EntityNotFoundException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@Stateless
public class ArticleDAO implements IArticleDAO {

	private ArticleHome home;
	private ArticleListHandler list;

	public ArticleDAO() {
		this.home = new ArticleHome();
		this.list = new ArticleListHandler();
	}

	@Override
	public ArticleId create(ArticleTO article) throws DAOException {
		ArticleId id = null;
		try {
			Article entity = ArticleConvertor.convertToEntity(article);
			this.home.setInstance(entity);
			this.home.save();
			id = new ArticleId(this.home.getInstance().getId());
		} catch (EntityExistsException e) {
			throw new DuplicateEntryException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return id;
	}

	@Override
	public ArticleTO get(ArticleId id) throws DAOException {
		if (id == null) {
			return null;
		}

		ArticleTO article = null;
		try {
			this.home.setId(id.getId());

			Article entity = this.home.getInstance();
			if (entity == null) {
				return null;
			}
			article = ArticleConvertor.convertFromEntity(entity);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return article;
	}

	@Override
	public void update(ArticleTO article) throws DAOException {
		try {
			Article entity = ArticleConvertor.convertToEntity(article);
			entity.setModified(new Date());
			this.home.setInstance(entity);
			this.home.save();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(ArticleId id) throws DAOException {
		try {
			this.home.setId(id.getId());
			Article article = this.home.getInstance();

			if (article == null) {
				throw new EntityNotFoundException("Can't delete article id: "
						+ id);
			}

			this.home.delete();
		} catch (EntityNotFoundException e) {

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<ArticleTO> findAll() throws DAOException {
		List<ArticleTO> articles = null;
		try {
			this.list.findAll();
			List<Article> entityList = this.list.getNextElements(20);
			articles = ArticleConvertor.convertList(entityList);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return articles;
	}

	@Override
	public List<ArticleTO> findEverywhere(String what) throws DAOException {
		List<ArticleTO> articles = null;
		try {
			this.list.findEverywhere(what);
			List<Article> entityList = this.list.getNextElements(20);
			articles = ArticleConvertor.convertList(entityList);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return articles;
	}

	@Override
	public ArticleTO getByTitle(String title) throws DAOException {
		ArticleTO article = null;
		try {
			Article entity = this.home.getArticleByTitle(title);
			if (entity == null) {
				return null;
			}
			article = ArticleConvertor.convertFromEntity(entity);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return article;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTO> findAll(int from, int maxCount) {
		EntityManagerFactory connection = ConnectionFactory.getInstance()
				.getConnection();
		EntityManager em = connection.createEntityManager();
		List<Article> articles = null;
		Query query = em.createNamedQuery("Article.findAll");
		query.setFirstResult(from);
		query.setMaxResults(maxCount);
		articles = query.getResultList();
		return ArticleConvertor.convertList(articles);
	}

	@Override
	public int getCount() {
		EntityManagerFactory connection = ConnectionFactory.getInstance()
				.getConnection();
		EntityManager em = connection.createEntityManager();
		Query query = em.createNamedQuery("Article.count");
		long count = (Long) query.getSingleResult();
		return (int)count;
	}

}
