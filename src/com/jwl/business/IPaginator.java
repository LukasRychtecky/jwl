package com.jwl.business;

import java.util.List;

import com.jwl.business.article.ArticleTO;

public interface IPaginator {

	public abstract boolean hasNext();

	public abstract boolean hasPrevious();

	public abstract int getLastPageIndex();

	public abstract int getFirstPageIndex();

	public abstract List<ArticleTO> getcurrentPageArticles();

	public abstract void setUpPaginator();

	public int getPageIndex();

	public int getPreviousPageIndex();

	public int getNextPageIndex();

}