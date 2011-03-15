package com.jwl.integration;

import com.jwl.integration.article.ArticleDAO;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;

/**
 * 
 * @author ostatnickyjiri
 */
public class JPADAOFactory extends DAOFactory {

	@Override
	protected IArticleDAO factoryArticle() {
		return new ArticleDAO();
	}

	@Override
	protected IHistoryDAO factoryHistory() {
		return new TagDAO();
	}

	@Override
	protected IRoleDAO factoryRole() {
		return new HistoryDAO();
	}

	@Override
	protected ITagDAO factoryTag() {
		// TODO Auto-generated method stub
		return null;
	}

}
