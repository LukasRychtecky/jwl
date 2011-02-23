package com.jwl.business.usecases;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IFindArticleByTitleUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class FindArticleByTitleUC extends AbstractUC implements IFindArticleByTitleUC {

	public FindArticleByTitleUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public ArticleTO find(String title) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_VIEW);
		ArticleTO article = null;
		try {
			article = super.factory.getArticleDAO().getByTitle(title);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		return article;
	}

}
