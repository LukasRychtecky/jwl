package com.jwl.integration;

import com.jwl.integration.article.ArticleDAO;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.HistoryDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.rating.RatingDAO;
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
	private IRatingDAO ratingDAO = null;

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

	@Override
	public IRatingDAO getRAtingDAO() {
		if(this.ratingDAO ==null){
			this.ratingDAO = new RatingDAO();
		}
		return this.ratingDAO;
	}

}
