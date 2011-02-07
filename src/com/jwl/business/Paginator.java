package com.jwl.business;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.ConnectionFactory;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.entity.Article;
import com.jwl.presentation.article.enumerations.ListColumns;

public class Paginator extends AbstractPaginator {
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
	public List<ArticleTO> getcurrentPageArticles() {
		List<Article> articles = null;
		Query query = this.em.createNamedQuery(this.getQueryName());
		query.setFirstResult((this.pageIndex - 1) * this.pageSize);
		query.setMaxResults(this.pageSize);
		articles = query.getResultList();
		return ArticleConvertor.convertList(articles);
	}

	private String getQueryName() {
		if (this.ascendingOrder) {
			if (this.orderByColumn.equals(ListColumns.CREATED)) {
				return OB_CREATED_A;
			}
			if (this.orderByColumn.equals(ListColumns.EDITOR)) {
				return OB_EDITOR_A;
			}
			return OB_TITLE_A;
		}
		if (this.orderByColumn.equals(ListColumns.CREATED)) {
			return OB_CREATED_D;
		}
		if (this.orderByColumn.equals(ListColumns.EDITOR)) {
			return OB_EDITOR_D;
		}
		return OB_TITLE_D;
	}

	@Override
	public void setUpPaginator() {
		EntityManagerFactory connection = ConnectionFactory.getInstance()
				.getConnection();
		em = connection.createEntityManager();
		super.setUpPaginator();
	}

}
