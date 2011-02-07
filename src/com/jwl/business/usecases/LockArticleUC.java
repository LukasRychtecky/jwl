package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.ILockArticleUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class LockArticleUC extends AbstractUC implements ILockArticleUC {

	private IArticleDAO dao;

	public LockArticleUC(IArticleDAO dao) {
		this.dao = dao;
	}

	@Override
	public void lock(ArticleId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_LOCK);
		try {
			ArticleTO article = this.dao.get(id);
			if (article == null) {
				throw new ObjectNotFoundException("Article not found, id: " + id);
			}
			article.setLocked(Boolean.TRUE);
			this.dao.update(article);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
