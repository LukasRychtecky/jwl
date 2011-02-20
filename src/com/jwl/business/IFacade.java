package com.jwl.business;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.IIdentity;
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


	public ArticleTO findArticleByTitle(String title) throws ModelException;

	/**
	 * Returns identity that represents the user of application.
	 * 
	 * @return
	 */
	public IIdentity getIdentity();

	public void uploadFile(HttpServletRequest request);

	public void makeDownloadFileResponse(HttpServletRequest request,
			HttpServletResponse response);

	public void lockArticle(ArticleId id) throws ModelException;

	public void unlockArticle(ArticleId id) throws ModelException;

	public ArticleTO getArticle(ArticleId id) throws ModelException;

	public IPaginator getPaginator();

	public IPaginator getSearchPaginator();

	public void setSearchParametres(SearchTO searchTO);

	public List<HistoryTO> getHistories(ArticleId id) throws ModelException;

	public HistoryTO getHistory(HistoryId id) throws ModelException;

	public void restoreArticle(HistoryId id) throws ModelException;

}
