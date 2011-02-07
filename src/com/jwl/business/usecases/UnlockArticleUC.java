package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IUnlockArticleUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class UnlockArticleUC extends AbstractUC implements IUnlockArticleUC {

	private IArticleDAO dao;

	public UnlockArticleUC(IArticleDAO dao) {
		this.dao = dao;
	}

	@Override
	public void unlock(ArticleId id) throws ModelException {
		try {
			super.checkPermission(AccessPermissions.ARTICLE_LOCK);
			ArticleTO article = this.dao.get(id);
			if (article == null) {
				throw new ObjectNotFoundException("Article not found, id: " + id);
			}
			article.setLocked(Boolean.FALSE);
			this.dao.update(article);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
