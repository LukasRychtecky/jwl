package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
import com.jwl.business.usecases.interfaces.IUpdateArticleUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class UpdateArticleUC extends AbstractUC implements IUpdateArticleUC {

	public UpdateArticleUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void update(ArticleTO article) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_EDIT);

		Set<String> tags = article.getTags();
		try {
			ArticleTO articleFromDB = super.factory.getArticleDAO().get(article.getId());

			if (articleFromDB == null) {
				throw new ObjectNotFoundException("Article not found, id: " + article.getId());
			}

			if (this.isArticleChanged(article, articleFromDB)) {

				article.setTitle(articleFromDB.getTitle());

				HistoryTO history = articleFromDB.createHistory();
				history.setId(new HistoryId(0, article.getId()));
				super.factory.getHistoryDAO().create(history);

				article.removeAllTags();
				article.setEditCount(articleFromDB.getEditCount() + 1);
				article.setTitle(articleFromDB.getTitle());
				article.setAttachments(articleFromDB.getAttachments());
				super.factory.getArticleDAO().update(article);
			}

		} catch (DAOException e) {
			throw new ModelException(e);
		}

		this.saveTags(tags, article.getId());
	}

	private Boolean isArticleChanged(ArticleTO article, ArticleTO articleFromDB) {
		Boolean changed = Boolean.FALSE;

		if (!article.getText().equals(articleFromDB.getText())) {
			return Boolean.TRUE;
		}

		return changed;
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
