package com.jwl.business;

import com.jwl.business.article.ArticleTO;

public class ArticlePair {
	private ArticleTO article1;
	private ArticleTO article2;

	public ArticlePair(ArticleTO article1, ArticleTO article2) {
		this.article1 = article1;
		this.article2 = article2;
	}

	public ArticleTO getArticle1() {
		return article1;
	}

	public ArticleTO getArticle2() {
		return article2;
	}
	
}
