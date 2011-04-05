package com.jwl.integration.post;

import java.util.List;

import com.jwl.business.article.PostTO;
import com.jwl.integration.exceptions.DAOException;

public interface IPostDAO {
	
	public void create(PostTO post, Integer topicId) throws DAOException;
	
	public List<PostTO> getPosts(Integer topicId) throws DAOException;
	
	public void delete(Integer postId) throws DAOException;
}
