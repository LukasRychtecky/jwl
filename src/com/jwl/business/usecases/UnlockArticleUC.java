package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IUnlockArticleUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class UnlockArticleUC extends AbstractUC implements IUnlockArticleUC {

	public UnlockArticleUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public void unlock(ArticleId id) throws ModelException {
		try {
			super.checkPermission(AccessPermissions.ARTICLE_LOCK);
			ArticleTO article = super.factory.getArticleDAO().get(id);
			if (article == null) {
				throw new ObjectNotFoundException("Article not found, id: " + id);
			}
			article.setLocked(Boolean.FALSE);
			super.factory.getArticleDAO().update(article);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
