package com.jwl.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.integration.ConnectionFactory;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.entity.Article;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.presentation.enumerations.JWLTableHeaders;

public class SearchPaginator extends AbstractArticlePaginator {

	private boolean searchInEditors;
	private boolean searchInTags;
	private boolean searchInText;
	private boolean searchInTitle;
	private String searchText;

	public SearchPaginator(int pageSize) {
		this.pageSize = pageSize;
		this.ascendingOrder = true;
		this.pageIndex = 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleTO> getCurrentPageContent() {
		List<ArticleTO> result =null;
		List<Article> articles = null;
		if (this.searchText.isEmpty()) {
			return new ArrayList<ArticleTO>();
		}
		EntityManager em;
		try{
		em = super.getEntityManager();	
		Query query = em.createQuery(this.buildQuery());
		query.setParameter(0, "%" + this.searchText + "%");
		query.setFirstResult((this.pageIndex - 1) * this.pageSize);
		query.setMaxResults(this.pageSize);
		articles = query.getResultList();
		 result = ArticleConvertor.convertList(articles);
		super.closeEntityManager(em);
		}catch(DAOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}

	public void setSearchCategories(SearchTO searchTO) {
		this.searchText = searchTO.getSearchPhrase().toLowerCase();
		if (searchTO.isTags()) {
			this.searchInTags = true;
		}
		if (searchTO.isEditors()) {
			this.searchInEditors = true;
		}
		if (searchTO.isKeyWords()) {
			this.searchInText = true;
		}
		if (searchTO.isTitle()) {
			this.searchInTitle = true;
		}
	}

	private String buildQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a ");
		sb.append(this.buildFromPart());
		sb.append(this.buildWherePart());
		sb.append(this.buildOrderByPart());
		return sb.toString();
	}
	
	private String buildFromPart(){
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Article a ");
		if(this.searchInTags){
			sb.append("LEFT OUTER JOIN a.tags t ");
		}
		return sb.toString();
	}

	private String buildWherePart() {
		boolean orNeeded = false;
		StringBuilder sb = new StringBuilder();
		if (this.searchInEditors|| this.searchInTags
				|| this.searchInTitle || this.searchInText) {
			sb.append("WHERE ");
		}
		if (this.searchInTitle) {
			sb.append("LOWER(a.title) LIKE ?0 ");
			orNeeded = true;
		}
		if (this.searchInText) {
			if (orNeeded) {
				sb.append("OR ");
			}
			sb.append("LOWER(a.text) LIKE ?0 ");
			orNeeded = true;
		}
		if (this.searchInEditors) {
			if (orNeeded) {
				sb.append("OR ");
			}
			sb.append("LOWER(a.editor) LIKE ?0 ");
			orNeeded = true;
		}
		if (this.searchInTags) {
			if (orNeeded) {
				sb.append("OR ");
			}
			sb.append("t.name IS NOT NULL AND LOWER(t.name) LIKE ?0 ");
			orNeeded = true;
		}
		return sb.toString();
	}

	private String buildOrderByPart() {
		StringBuilder sb = new StringBuilder();
		sb.append("ORDER BY ");
		if (this.orderByColumn.equals(JWLTableHeaders.CREATED)) {
			sb.append("a.created ");
		} else if (this.orderByColumn.equals(JWLTableHeaders.EDITOR)) {
			sb.append("a.editor ");
		} else {
			sb.append("a.title ");
		}
		if (this.ascendingOrder) {
			sb.append("ASC ");
		} else {
			sb.append("DESC ");
		}
		return sb.toString();
	}

	private void setArticleCount() throws DAOException {
		EntityManager em = super.getEntityManager();
		
		Query q = em.createQuery(this.buildResultCountQuery());
		q.setParameter(0, "%" + this.searchText + "%");
		Long count = (Long) q.getSingleResult();
		this.articleCount = count.intValue();
		
		super.closeEntityManager(em);
	}

	private String buildResultCountQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(a) ");
		sb.append(this.buildFromPart());
		sb.append(this.buildWherePart());
		return sb.toString();
	}

	@Override
	public void setUpPaginator() {
		super.setUpPaginator();
		if (searchText != null) {
			try{
				this.setArticleCount();
			}catch(DAOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.articleCount = 0;
		}
	}

}
