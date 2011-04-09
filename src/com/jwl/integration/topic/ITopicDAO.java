package com.jwl.integration.topic;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;
import com.jwl.integration.exceptions.DAOException;

public interface ITopicDAO {
	
	public Integer create(TopicTO topic, ArticleId articleId)throws DAOException;
	
	public void delete(Integer id) throws DAOException;
	
	public void close(Integer id) throws DAOException;
	
	public void open(Integer id) throws DAOException;
	
	public TopicTO find(Integer id) throws DAOException;
	
}
