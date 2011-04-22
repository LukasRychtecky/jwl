package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;

public interface ICreateForumTopicUC {
	
	public Integer createTopic(TopicTO topic, ArticleId articleId) throws ModelException;
}
