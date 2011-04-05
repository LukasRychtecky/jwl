package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.PostTO;
import com.jwl.business.exceptions.ModelException;

public interface IAddForumPostUC {
	
	public void add(PostTO post, Integer topicId) throws ModelException;
}
