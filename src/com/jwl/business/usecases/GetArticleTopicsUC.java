package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetArticleTopicsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

public class GetArticleTopicsUC extends AbstractUC implements
		IGetArticleTopicsUC {

	public GetArticleTopicsUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<TopicTO> getArticleTopics(ArticleId articleId)
			throws ModelException {
		checkPermission(AccessPermissions.FORUM_VIEW, articleId);
		List<TopicTO> topics =null;
		try {
			ArticleTO article = factory.getArticleDAO().get(articleId);
			topics = article.getTopics();
		} catch (DAOException e) {
			throw new ModelException(e);
		}
		return topics;
	}

}
