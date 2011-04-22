package com.jwl.business;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLTableHeaders;
import com.jwl.presentation.url.WikiURLParser;

public abstract class AbstractArticlePaginator implements IPaginator<ArticleTO> {

	private WikiURLParser wup;
	protected int pageSize;
	protected EntityManager em;
	protected String orderByColumn;
	protected int pageIndex;
	protected boolean ascendingOrder;
	protected int articleCount;

	public AbstractArticlePaginator() {
		super();
	}

	@Override
	public boolean hasNext() {
		if (this.pageIndex * this.pageSize < this.articleCount) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPrevious() {
		if (this.pageIndex != 1) {
			return true;
		}
		return false;
	}

	@Override
	public int getLastPageIndex() {
		if (this.articleCount % this.pageSize > 0) {
			return this.articleCount / this.pageSize + 1;
		}
		return this.articleCount / this.pageSize;
	}

	@Override
	public int getFirstPageIndex() {
		return 1;
	}

	@Override
	public void setUpPaginator() {
		wup = new WikiURLParser();
		this.setPageNumber();
		this.setOrderByColumn();
		this.setArticleCount();
	}

	private void setPageNumber() {
		String pn = wup.getListPageNumber();
		if (pn == null) {
			return;
		}
		try {
			this.pageIndex = Integer.parseInt(pn);
		} catch (Exception e) {
			this.pageIndex = 1;
		}
	}

	private void setOrderByColumn() {
		String columnName = wup.getListOrderByColumn();
		if (columnName != null) {
			if (this.orderByColumn.equals(columnName)) {
				if (this.ascendingOrder == true) {
					this.ascendingOrder = false;
				} else {
					this.ascendingOrder = true;
				}
			} else {
				this.ascendingOrder = true;
			}
			this.orderByColumn = columnName;
			this.pageIndex = 1;
		} else {
			if (this.orderByColumn == null) {
				this.orderByColumn = JWLTableHeaders.TITLE.value;
			}
		}
	}

	@Override
	public int getPageIndex() {
		return this.pageIndex;
	}

	@Override
	public int getPreviousPageIndex() {
		return this.pageIndex - 1;
	}

	@Override
	public int getNextPageIndex() {
		return this.pageIndex + 1;
	}

	private void setArticleCount() {
		Query q = em.createNamedQuery("Article.count");
		Long count = (Long) q.getSingleResult();
		this.articleCount = count.intValue();
	}

	@Override
	public int getCurrentPageFirst(){
		return 0;
	}

	@Override
	public int getCurrentPageLast(){
		return 0;
	}

	@Override
	public int getContentSize(){
		return 0;
	}

}