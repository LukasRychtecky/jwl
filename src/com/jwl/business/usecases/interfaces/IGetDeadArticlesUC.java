package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;

public interface IGetDeadArticlesUC {
	
	public List<ArticleTO> getDeadArticles() throws ModelException;
}
