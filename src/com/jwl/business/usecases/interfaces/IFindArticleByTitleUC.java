package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IFindArticleByTitleUC {

	/**
	 * Finds ArticleTO by given title and return, otherwise return null.
	 *
	 * @param title
	 * @return ArticleTO|null
	 * @throws ModelException
	 */
	public ArticleTO find(String title) throws ModelException;

}
