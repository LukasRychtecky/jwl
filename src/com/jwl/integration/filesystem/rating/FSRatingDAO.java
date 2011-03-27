package com.jwl.integration.filesystem.rating;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.RatingTO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.rating.IRatingDAO;

public class FSRatingDAO implements IRatingDAO {

	@Override
	public void create(RatingTO rating, ArticleId articleId)
			throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public RatingTO find(ArticleId articleId, String author)
			throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void update(RatingTO rating, ArticleId articleId)
			throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
