package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;

public interface IGetTopicUC {
	public TopicTO getTopic(Integer topicId) throws ModelException;
}
