package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;

public interface IRateArticleUC {
	public void rateArticle(ArticleId articleId, float rating)throws ModelException;
}
