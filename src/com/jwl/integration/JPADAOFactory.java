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
 * @author ostatnickyjiri
 */
public class JPADAOFactory extends DAOFactory {

	@Override
	protected IArticleDAO factoryArticle() {
		return new ArticleDAO();
	}

	@Override
	protected IHistoryDAO factoryHistory() {
		return new HistoryDAO();
	}

	@Override
	protected IRoleDAO factoryRole() {
		return new RoleDAO();
	}

	@Override
	protected ITagDAO factoryTag() {
		return new TagDAO();
	}

	@Override
	protected IRatingDAO factoryRating() {
		return new RatingDAO();
	}

	

}
