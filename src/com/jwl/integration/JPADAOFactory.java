package com.jwl.integration;

import com.jwl.integration.article.ArticleDAO;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.HistoryDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.role.RoleDAO;
import com.jwl.integration.tag.ITagDAO;
import com.jwl.integration.tag.TagDAO;

/**
 *
 * @author Lukas Rychtecky
 */
public class JPADAOFactory implements IDAOFactory {

	private IArticleDAO articleDAO = null;
	private ITagDAO tagDAO = null;
	private IHistoryDAO historyDAO = null;
	private IRoleDAO roleDAO = null;

	@Override
	public IArticleDAO getArticleDAO() {
		if (this.articleDAO == null) {
			this.articleDAO = new ArticleDAO();
		}
		return this.articleDAO;
	}

	@Override
	public ITagDAO getTagDAO() {
		if (this.tagDAO == null) {
			this.tagDAO = new TagDAO();
		}
		return this.tagDAO;
	}

	@Override
	public IHistoryDAO getHistoryDAO() {
		if (this.historyDAO == null) {
			this.historyDAO = new HistoryDAO();
		}
		return this.historyDAO;
	}

	@Override
	public IRoleDAO getRoleDAO() {
		if (this.roleDAO == null) {
			this.roleDAO = new RoleDAO();
		}
		return this.roleDAO;
	}

}
