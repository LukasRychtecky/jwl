package com.jwl.integration;

import com.jwl.integration.article.ArticleDAO;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.HistoryDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.keyword.IKeyWordDAO;
import com.jwl.integration.keyword.KeyWordDAO;
import com.jwl.integration.post.IPostDAO;
import com.jwl.integration.post.PostDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.rating.RatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.role.RoleDAO;
import com.jwl.integration.tag.ITagDAO;
import com.jwl.integration.tag.TagDAO;
import com.jwl.integration.topic.ITopicDAO;
import com.jwl.integration.topic.TopicDAO;

/**
 * 
 * @author ostatnickyjiri
 */
public class JPADAOFactory extends DAOFactory {

	private IKeyWordDAO keyWordDAO = null;
	private ITopicDAO topicDAO = null;
	private IPostDAO postDAO = null;
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
	public IRatingDAO getRatingDAO() {
		if (this.ratingDAO == null) {
			this.ratingDAO = new RatingDAO();
		}
		return this.ratingDAO;
	}

	@Override
	public IKeyWordDAO getKeyWordDAO() {
		if(this.keyWordDAO == null){
			this.keyWordDAO = new KeyWordDAO();
		}
		return this.keyWordDAO;
	}

	@Override
	public ITopicDAO getTopicDAO() {
		if(this.topicDAO == null){
			this.topicDAO = new TopicDAO();
		}
		return this.topicDAO;
	}

	@Override
	public IPostDAO getPostDAO() {
		if(this.postDAO == null){
			this.postDAO = new PostDAO();
		}
		return this.postDAO;
	}

	@Override
	protected IRatingDAO factoryRating() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
