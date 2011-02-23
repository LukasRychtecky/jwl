package com.jwl.business.knowledge;

import com.jwl.business.article.ArticleTO;

public interface IArticleIterator {
	public boolean hasNext();
	public ArticleTO getNextArticle();
	
}
