package com.jwl.integration;

import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IDAOFactory {

	public IArticleDAO getArticleDAO();

	public ITagDAO getTagDAO();

	public IHistoryDAO getHistoryDAO();

	public IRoleDAO getRoleDAO();
	
	public IRatingDAO getRAtingDAO();

}
