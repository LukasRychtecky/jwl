package com.jwl.integration;

import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.keyword.IKeyWordDAO;
import com.jwl.integration.post.IPostDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;
import com.jwl.integration.topic.ITopicDAO;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IDAOFactory {

	public IArticleDAO getArticleDAO();

	public ITagDAO getTagDAO();

	public IHistoryDAO getHistoryDAO();

	public IRoleDAO getRoleDAO();
	
	public IRatingDAO getRatingDAO();
	
	public IKeyWordDAO getKeyWordDAO();
	
	public ITopicDAO getTopicDAO();
	
	public IPostDAO getPostDAO();

}
