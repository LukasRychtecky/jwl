package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IGetArticleUC {

	/**
	 * Return ArticleTO by given id, otherwise null
	 *
	 * @param id
	 * @return ArticleTO|null
	 * @throws ModelException
	 */
	public ArticleTO get(ArticleId id) throws ModelException;
}
