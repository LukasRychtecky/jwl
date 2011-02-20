package com.jwl.business.usecases;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IRestoreArticleUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class RestoreArticleUC extends AbstractUC implements IRestoreArticleUC {

	public RestoreArticleUC(IDAOFactory factory) {
		super(factory);
	}
	

	@Override
	public void restore(HistoryId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_RESTORE);
		
		try {
			ArticleTO article = this.getArticle(id);
			HistoryTO history = this.getHistory(id);
			this.restoreArticle(article, history);

			super.factory.getHistoryDAO().deleteAllYoungerThan(id.getArticleId(), history.getModified());
		} catch (DAOException e) {
			throw new ModelException(e);
		}

	}

	private void restoreArticle(ArticleTO article, HistoryTO history) throws DAOException {
		article.setChangeNote(history.getChangeNote());
		article.setEditor(history.getEditor());
		article.setText(history.getText());
		article.setTitle(history.getTitle());

		super.factory.getArticleDAO().update(article);
	}

	private ArticleTO getArticle(HistoryId id) throws DAOException, ObjectNotFoundException {
		ArticleTO article = null;
		article = super.factory.getArticleDAO().get(id.getArticleId());
		if (article == null) {
			throw new ObjectNotFoundException("Article not found, id: " + id.getArticleId());
		}
		return article;
	}

	private HistoryTO getHistory(HistoryId id) throws DAOException, ObjectNotFoundException {
		HistoryTO history = null;
		history = super.factory.getHistoryDAO().get(id);
		if (history == null) {
			throw new ObjectNotFoundException("History not found, id: " + id);
		}
		return history;
	}

}
