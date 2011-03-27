package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;

public interface IGetSimilarArticlesInViewUC {
	
	public List<ArticleTO> getSimilarArticles(ArticleTO article) throws ModelException;
}
