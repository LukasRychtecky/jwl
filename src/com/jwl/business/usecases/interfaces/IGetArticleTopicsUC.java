package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;

public interface IGetArticleTopicsUC {
	
	public List<TopicTO> getArticleTopics(ArticleId articleId) throws ModelException;
}
