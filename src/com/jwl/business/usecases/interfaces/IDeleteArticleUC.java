package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IDeleteArticleUC {

	public void delete(ArticleId id) throws ModelException;

}
