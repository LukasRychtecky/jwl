package com.jwl.integration;

import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.rating.RatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;

/**
 * 
 * @author Lukas Rychtecky
 */
public abstract class DAOFactory implements IDAOFactory {

	private IArticleDAO articleDAO = null;
	private ITagDAO tagDAO = null;
	private IHistoryDAO historyDAO = null;
	private IRoleDAO roleDAO = null;
	protected IRatingDAO ratingDAO = null;

	@Override
	public IArticleDAO getArticleDAO() {
		if (this.articleDAO == null) {
			this.articleDAO = factoryArticle();
		}
		return this.articleDAO;
	}

	@Override
	public ITagDAO getTagDAO() {
		if (this.tagDAO == null) {
			this.tagDAO = factoryTag();
		}
		return this.tagDAO;
	}

	@Override
	public IHistoryDAO getHistoryDAO() {
		if (this.historyDAO == null) {
			this.historyDAO = factoryHistory();
		}
		return this.historyDAO;
	}

	@Override
	public IRoleDAO getRoleDAO() {
		if (this.roleDAO == null) {
			this.roleDAO = factoryRole();
		}
		return this.roleDAO;
	}

	@Override
	public IRatingDAO getRatingDAO() {
		if (this.ratingDAO == null) {
			this.ratingDAO = new RatingDAO();
		}
		return this.ratingDAO;
	}

	protected abstract IArticleDAO factoryArticle();

	protected abstract ITagDAO factoryTag();

	protected abstract IHistoryDAO factoryHistory();

	protected abstract IRoleDAO factoryRole();

	protected abstract IRatingDAO factoryRating();

}
