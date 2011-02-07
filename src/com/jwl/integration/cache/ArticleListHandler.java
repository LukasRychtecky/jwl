package com.jwl.integration.cache;

import java.util.List;

import com.jwl.business.exceptions.ListHandlerJWLException;
import com.jwl.integration.ConnectionFactory;
import com.jwl.integration.entity.Article;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class ArticleListHandler extends ValueListHandler<Article> {

	private static final String FIND_ALL = "Article.findAll";
	private static final String FIND_EVERYWHERE = "Article.findEverywhere";

	private EntityManagerFactory factory;

	public ArticleListHandler() {
		this.factory = ConnectionFactory.getInstance().getConnection();
	}

	@SuppressWarnings("unchecked")
	public void findAll() throws ListHandlerJWLException {
		try {
			List result = getNamedQueryResult(ArticleListHandler.FIND_ALL);
			super.setList(result);
		} catch (Exception e) {
			throw new ListHandlerJWLException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void findEverywhere(String what) throws ListHandlerJWLException {
		String[] params = new String[]{"%" + what + "%"};
		try {
			List result = getNamedQueryResult(
					ArticleListHandler.FIND_EVERYWHERE, params);
			super.setList(result);
		} catch (Exception e) {
			throw new ListHandlerJWLException(e);
		}
	}
	
	private List<?> getNamedQueryResult(String namedQuery) {
		return this.getNamedQueryResult(namedQuery, new String[]{});
	}
	
	private List<?> getNamedQueryResult(String namedQuery, String[] params) {
		EntityManager manager = this.factory.createEntityManager();
		Query query = manager.createNamedQuery(namedQuery);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.getResultList();
	}
	
	

}
