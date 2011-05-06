package com.jwl.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.ConnectionFactory;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.entity.Article;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.presentation.enumerations.JWLTableHeaders;

public class Paginator extends AbstractArticlePaginator {
	private static String OB_TITLE_A = "Article.allOrderedByTitleASC";
	private static String OB_TITLE_D = "Article.allOrderedByTitleDESC";
	private static String OB_CREATED_A = "Article.allOrderedByCreatedASC";
	private static String OB_CREATED_D = "Article.allOrderedByCreatedDESC";
	private static String OB_EDITOR_A = "Article.allOrderedByEdiotrASC";
	private static String OB_EDITOR_D = "Article.allOrderedByEditorDESC";

	public Paginator(int pageSize) {
		this.pageSize = pageSize;
		this.ascendingOrder = true;
		this.pageIndex = 1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ArticleTO> getCurrentPageContent() {
		List<ArticleTO> result = null;
		try{
		EntityManager em = super.getEntityManager();
		Query query = em.createNamedQuery(this.getQueryName());
		query.setFirstResult((this.pageIndex - 1) * this.pageSize);
		query.setMaxResults(this.pageSize);
		List<Article> articles = query.getResultList();
		result = ArticleConvertor.convertList(articles);
		super.closeEntityManager(em);
		}catch(DAOException e){
			
		}
		return result;
	}

	private String getQueryName() {
		if (this.ascendingOrder) {
			if (this.orderByColumn.equals(JWLTableHeaders.CREATED)) {
				return OB_CREATED_A;
			}
			if (this.orderByColumn.equals(JWLTableHeaders.EDITOR)) {
				return OB_EDITOR_A;
			}
			return OB_TITLE_A;
		}
		if (this.orderByColumn.equals(JWLTableHeaders.CREATED)) {
			return OB_CREATED_D;
		}
		if (this.orderByColumn.equals(JWLTableHeaders.EDITOR)) {
			return OB_EDITOR_D;
		}
		return OB_TITLE_D;
	}

	@Override
	public void setUpPaginator() {
		super.setUpPaginator();
	}

}
