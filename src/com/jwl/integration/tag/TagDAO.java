package com.jwl.integration.tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.ArticleId;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.TagConvertor;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.Tag;
import com.jwl.integration.exceptions.DAOException;

/**
 * 
 * @author Lukas Rychtecky
 */
public class TagDAO extends BaseDAO implements ITagDAO {
	private static final String GET_ALL = "Tag.getAll";

	@Override
	public void create(String tag, ArticleId id) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			Tag entity = new Tag(tag);
			Article article = new Article();
			article.setId(id.getId());
			Set<Article> articles = new HashSet<Article>(1);
			articles.add(article);
			entity.setArticles(articles);
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			em.persist(entity);
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
	public void create(Set<String> tags, ArticleId id) throws DAOException {
		for (String tag : tags) {
			this.create(tag, id);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getAll() throws DAOException {
		Set<String> tags = new HashSet<String>();
		EntityManager em = getEntityManager();
		try {
			Query query = em.createNamedQuery(TagDAO.GET_ALL);

			List<Tag> entities = (List<Tag>) query.getResultList();
			for (Tag tag : entities) {
				tags.add(tag.getName());
			}
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
		return tags;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getAllWhere(Set<String> tags) throws DAOException {
		Set<String> result = new HashSet<String>();
		EntityManager em = getEntityManager();
		if (tags.isEmpty()) {
			return result;
		}

		try {
			StringBuilder query = new StringBuilder(
					"SELECT t FROM Tag t WHERE name IN (");
			for (int i = 0; i < tags.size(); i++) {
				query.append("?, ");
			}

			String s = query.substring(0, query.length() - 2).concat(")");
			Query select = em.createQuery(s);

			int i = 0;
			for (String tag : tags) {
				select.setParameter(i + 1, tag);
				i++;
			}
			result.addAll(TagConvertor.toStringSet((List<Tag>) select
					.getResultList()));
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getAllWhere(ArticleId id) throws DAOException {
		Set<String> result = new HashSet<String>();
		EntityManager em = getEntityManager();

		try {
			Query query = em
					.createQuery("SELECT t FROM Tag t LEFT OUTER JOIN t.articles a WHERE a.id = :articleId");
			query.setParameter("articleId", id.getId());

			List<Tag> tags = query.getResultList();
			for (Tag tag : tags) {
				System.out.println("T: " + tag.getName());
			}
			result.addAll(TagConvertor.toStringSet((Collection<Tag>) tags));

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
		return result;
	}

	@Override
	public void addExistingToArticle(Set<String> tags, ArticleId id)
			throws DAOException {
		if (tags.isEmpty()) {
			return;
		}

		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();

		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			StringBuilder query = new StringBuilder(
					"REPLACE INTO article_has_tag (");
			query.append("SELECT tag.id as tag_id, ? as article_id ");
			query.append("FROM tag ");
			query.append("WHERE tag.name IN (");
			for (Integer index = 0; index < tags.size(); index++) {
				query.append("?").append(", ");
			}

			Query insert = em.createNativeQuery(query.substring(0,
					query.length() - 2).concat("))"));
			insert.setParameter(1, id.getId());

			Integer index = 2;
			for (String tag : tags) {
				insert.setParameter(index, tag);
				index++;
			}
			insert.executeUpdate();
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
	public void removeFromArticle(Set<String> tags, ArticleId id)
			throws DAOException {
		if (tags.isEmpty()) {
			return;
		}

		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();

		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			StringBuilder query = new StringBuilder(
					"DELETE FROM article_has_tag ");
			query.append("WHERE article_id = :articleId AND tag_id IN (");
			for (Integer index = 0; index < tags.size(); index++) {
				query.append("?").append(", ");
			}

			Query insert = em.createNativeQuery(query.substring(0,
					query.length() - 2).concat(")"));
			insert.setParameter("articleId", id.getId());
			Integer index = 1;
			for (String tag : tags) {
				insert.setParameter(index, tag);
				index++;
			}
			insert.executeUpdate();
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
