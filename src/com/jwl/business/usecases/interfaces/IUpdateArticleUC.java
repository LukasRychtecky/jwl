package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IUpdateArticleUC {

	public void update(ArticleTO article) throws ModelException;

}
