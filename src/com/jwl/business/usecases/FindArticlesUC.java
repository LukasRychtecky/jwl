package com.jwl.business.usecases;

import com.jwl.integration.IDAOFactory;
import java.util.ArrayList;
import java.util.List;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissionsOld;
import com.jwl.business.usecases.interfaces.IFindArticlesUC;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class FindArticlesUC extends AbstractUC implements IFindArticlesUC {

	public FindArticlesUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<ArticleTO> find(SearchTO search) throws ModelException {
		super.checkPermission(AccessPermissionsOld.ARTICLE_VIEW);
		List<ArticleTO> articles = new ArrayList<ArticleTO>();
		try {
			articles.addAll(super.factory.getArticleDAO().findEverywhere(search.getSearchText()));
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		return articles;
	}

}
