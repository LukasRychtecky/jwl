package com.jwl.business.knowledge.util;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.exceptions.DAOException;

public interface IArticleIterator {
	public boolean hasNext();

	public ArticleTO getNextArticle() throws DAOException;

}
