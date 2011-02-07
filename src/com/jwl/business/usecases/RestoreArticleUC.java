package com.jwl.business.usecases;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IRestoreArticleUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.dao.interfaces.IHistoryDAO;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class RestoreArticleUC extends AbstractUC implements IRestoreArticleUC {
	
	private IArticleDAO articleDAO;
	private IHistoryDAO historyDAO;

	public RestoreArticleUC(IArticleDAO articleDAO, IHistoryDAO historyDAO) {
		this.articleDAO = articleDAO;
		this.historyDAO = historyDAO;
	}

	@Override
	public void restore(HistoryId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_RESTORE);
		
		try {
			ArticleTO article = this.getArticle(id);
			HistoryTO history = this.getHistory(id);
			this.restoreArticle(article, history);

			this.historyDAO.deleteAllYoungerThan(id.getArticleId(), history.getModified());
		} catch (DAOException e) {
			throw new ModelException(e);
		}

	}

	private void restoreArticle(ArticleTO article, HistoryTO history) throws DAOException {
		article.setChangeNote(history.getChangeNote());
		article.setEditor(history.getEditor());
		article.setText(history.getText());
		article.setTitle(history.getTitle());

		this.articleDAO.update(article);
	}

	private ArticleTO getArticle(HistoryId id) throws DAOException, ObjectNotFoundException {
		ArticleTO article = null;
		article = this.articleDAO.get(id.getArticleId());
		if (article == null) {
			throw new ObjectNotFoundException("Article not found, id: " + id.getArticleId());
		}
		return article;
	}

	private HistoryTO getHistory(HistoryId id) throws DAOException, ObjectNotFoundException {
		HistoryTO history = null;
		history = this.historyDAO.get(id);
		if (history == null) {
			throw new ObjectNotFoundException("History not found, id: " + id);
		}
		return history;
	}

}
