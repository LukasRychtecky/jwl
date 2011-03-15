package com.jwl.integration;

import com.jwl.integration.article.ArticleDAO;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.rating.RatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;

/**
 * 
 * @author ostatnickyjiri
 */
<<<<<<< HEAD
public class JPADAOFactory extends DAOFactory {
=======
public class JPADAOFactory implements IDAOFactory {

	private IArticleDAO articleDAO = null;
	private ITagDAO tagDAO = null;
	private IHistoryDAO historyDAO = null;
	private IRoleDAO roleDAO = null;
	private IRatingDAO ratingDAO = null;
>>>>>>> master

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

	@Override
	public IRatingDAO getRAtingDAO() {
		if(this.ratingDAO ==null){
			this.ratingDAO = new RatingDAO();
		}
		return this.ratingDAO;
	}

}
