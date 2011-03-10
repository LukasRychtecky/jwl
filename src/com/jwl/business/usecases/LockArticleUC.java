package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissionsOld;
import com.jwl.business.usecases.interfaces.ILockArticleUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class LockArticleUC extends AbstractUC implements ILockArticleUC {

	public LockArticleUC(IDAOFactory factory) {
		super(factory);
	}



	@Override
	public void lock(ArticleId id) throws ModelException {
		super.checkPermission(AccessPermissionsOld.ARTICLE_LOCK);
		try {
			ArticleTO article = super.factory.getArticleDAO().get(id);
			if (article == null) {
				throw new ObjectNotFoundException("Article not found, id: " + id);
			}
			article.setLocked(Boolean.TRUE);
			super.factory.getArticleDAO().update(article);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
