package com.jwl.integration.keyword;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.KeyWordTO;
import com.jwl.integration.BaseDAO;
import com.jwl.integration.convertor.KeyWordConvertor;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.KeyWord;
import com.jwl.integration.exceptions.DAOException;

public class KeyWordDAO extends BaseDAO implements IKeyWordDAO {

	@Override
	public void create(KeyWordTO keyWord, ArticleId articleId)
			throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			KeyWord entity = KeyWordConvertor.toEntity(keyWord);
			Article article = em.find(Article.class, articleId.getId());
			entity.setArticle(article);
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
	public void delete(KeyWordTO keyWord) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			KeyWord entity = KeyWordConvertor.toEntity(keyWord);
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();
			em.remove(entity);
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
	public void deleteAll(ArticleId articleId) throws DAOException {
		UserTransaction ut = getUserTransaction();
		boolean localTrans = false;
		EntityManager em = getEntityManager();
		try {
			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();

			Article article = em.find(Article.class, articleId.getId());
			if (article != null) {
				for (KeyWord kw : article.getKeyWords()) {
					em.remove(kw);
				}
				em.flush();
			}
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
	public void create(List<KeyWordTO> keyWords, ArticleId articleId)
			throws DAOException {
		for (KeyWordTO keyWord : keyWords) {
			create(keyWord, articleId);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyWordTO> getAll() throws DAOException {
		EntityManager em = getEntityManager();
		List<KeyWord> result =null;
		try{
			Query query = em.createNamedQuery("KeyWord.findAll");
			result = query.getResultList();
		}catch(Throwable t){
			closeEntityManager(em);
		}
		return KeyWordConvertor.fromEntities(result);
	}

}
