package com.jwl.integration.rating;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.RatingTO;
import com.jwl.integration.exceptions.DAOException;

public interface IRatingDAO {
	public void create(RatingTO rating, ArticleId articleId)
			throws DAOException;

	public void update(RatingTO rating, ArticleId articleId)
			throws DAOException;

	public RatingTO find(ArticleId articleId, String author)
			throws DAOException;
}
