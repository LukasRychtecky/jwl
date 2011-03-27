package com.jwl.business.usecases;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetArticleUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetArticleUC extends AbstractUC implements IGetArticleUC {

	public GetArticleUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public ArticleTO get(ArticleId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_VIEW, id);
		ArticleTO article = null;
		try {
			article = super.factory.getArticleDAO().get(id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		Environment.getKnowledgeFacade().handleArticleViewLivability(article.getId());
		return article;
	}
}
