package com.jwl.business.usecases;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IFindArticlesUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class FindArticlesUC extends AbstractUC implements IFindArticlesUC {

	private IArticleDAO dao;

	public FindArticlesUC(IArticleDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<ArticleTO> find(SearchTO search) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_VIEW);
		List<ArticleTO> articles = new ArrayList<ArticleTO>();
		try {
			articles.addAll(this.dao.findEverywhere(search.getSearchText()));
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		return articles;
	}

}
