package com.jwl.business;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.business.security.IIdentity;
import java.io.File;
import javax.naming.NoPermissionException;

/**
 * This interface provides communication between Model(business tier,
 * integration tier) and Controller, View. The class is designed as Facade.
 */
public interface IFacade {

	public List<ArticleTO> findArticles(SearchTO searchTO) throws ModelException;

	/**
	 * Updates article data
	 * 
	 * @param articleTO
	 */
	public void updateArticle(ArticleTO article) throws ModelException, NoPermissionException;

	/**
	 * Creates new article
	 * 
	 * @param articleTO
	 */
	public void createArticle(ArticleTO article) throws ModelException, NoPermissionException;

	/**
	 * Deletes given article
	 * 
	 * @param ArticleId id
	 */
	public void deleteArticle(ArticleId id) throws ModelException;

	public void setJWLHome(String home);

	public String getJWLHome();

	public void importACL(String fileName) throws ModelException;


	public ArticleTO findArticleByTitle(String title) throws ModelException;

	/**
	 * Returns identity that represents the user of application.
	 * 
	 * @return
	 */
	public IIdentity getIdentity();

	public void uploadAttachment(AttachmentTO attachment, String source) throws ModelException;

	public File getFile(String name) throws ModelException;

	public void lockArticle(ArticleId id) throws ModelException;

	public void unlockArticle(ArticleId id) throws ModelException;

	public ArticleTO getArticle(ArticleId id) throws ModelException;

	public IPaginator<ArticleTO> getPaginator();

	public IPaginator<ArticleTO> getSearchPaginator();

	public void setSearchParametres(SearchTO searchTO);

	public List<HistoryTO> getHistories(ArticleId id) throws ModelException;

	public HistoryTO getHistory(HistoryId id) throws ModelException;

	public void restoreArticle(HistoryId id) throws ModelException;
	
	public void rateArticle(ArticleId id, float rating) throws ModelException;
	
	public List<ArticlePair> getMergeSuggestions() throws ModelException;
	
	public List<ArticleTO> getDeadArticles() throws ModelException;
	
	public void addToMergeSuggestionsIgnored(List<ArticleIdPair> articleIdPairs) throws ModelException;
	
	public void increaseLivability(List<ArticleId> ids, double increase) throws ModelException;
	
	public List<ArticleTO> getSimilarArticlesInView(ArticleTO article) throws ModelException;
	
	public IPaginator<TopicTO> getArticleForumTopics(ArticleId articleId) throws ModelException;
	
	public void createForumTopic(TopicTO topic, ArticleId article) throws ModelException;
	
	public void deleteForumTopics(List<Integer> topicIds) throws ModelException;
	
	public void closeForumTopics(List<Integer> topicIds) throws ModelException;
	
	public void openForumTopics(List<Integer> topicIds) throws ModelException;
	
}
