package com.jwl.integration.post;

import com.jwl.business.article.PostTO;
import com.jwl.integration.exceptions.DAOException;

public interface IPostDAO {
	
	public void create(PostTO post, Integer topicId) throws DAOException;
}
