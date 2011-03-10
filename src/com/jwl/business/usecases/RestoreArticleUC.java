package com.jwl.business.usecases;

import java.util.Set;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissionsOld;
import com.jwl.business.usecases.interfaces.IRestoreArticleUC;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
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
		super.checkPermission(AccessPermissionsOld.ARTICLE_RESTORE);
		
		try {
			ArticleTO article = this.getArticle(id);
			HistoryTO history = this.getHistory(id);
			Set<String> tags = article.getTags();
			this.restoreArticle(article, history);
			this.saveTags(tags, article.getId());
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
		article.removeAllTags();
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
	private void saveTags(Set<String> tags, ArticleId id) throws ModelException {

		ISaveTagsUC uc = new SaveTagsUC(super.factory);
		uc.save(tags, id);

		try {
			Set<String> allTags = super.factory.getTagDAO().getAllWhere(id);
			allTags.removeAll(tags);
			super.factory.getTagDAO().removeFromArticle(tags, id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
