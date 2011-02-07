package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
import com.jwl.business.usecases.interfaces.IUpdateArticleUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.dao.interfaces.IHistoryDAO;
import com.jwl.integration.dao.interfaces.ITagDAO;
import com.jwl.integration.exceptions.DAOException;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class UpdateArticleUC extends AbstractUC implements IUpdateArticleUC {

	private IArticleDAO dao;
	private IHistoryDAO historyDAO;
	private ITagDAO tagDAO;

	public UpdateArticleUC(IArticleDAO dao, IHistoryDAO historyDAO, ITagDAO tagDAO) {
		this.dao = dao;
		this.historyDAO = historyDAO;
		this.tagDAO = tagDAO;
	}

	@Override
	public void update(ArticleTO article) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_EDIT);

		Set<String> tags = article.getTags();
		try {
			ArticleTO articleFromDB = this.dao.get(article.getId());

			if (articleFromDB == null) {
				throw new ObjectNotFoundException("Article not found, id: " + article.getId());
			}

			if (this.isArticleChanged(article, articleFromDB)) {

				article.setTitle(articleFromDB.getTitle());

				HistoryTO history = article.createHistory();
				history.setId(new HistoryId(0, article.getId()));
				this.historyDAO.create(history);

				article.removeAllTags();
				article.setEditCount(article.getEditCount() + 1);
				article.setTitle(articleFromDB.getTitle());
				this.dao.update(article);
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

		ISaveTagsUC uc = new SaveTagsUC(this.tagDAO);
		uc.save(tags, id);

		try {
			Set<String> allTags = this.tagDAO.getAllWhere(id);
			allTags.removeAll(tags);
			this.tagDAO.removeFromArticle(tags, id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
