package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetSimilarArticlesInViewUC;
import com.jwl.integration.IDAOFactory;

public class GetSimilarArticlesInViewUC extends AbstractUC implements
		IGetSimilarArticlesInViewUC {

	public GetSimilarArticlesInViewUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<ArticleTO> getSimilarArticles(ArticleTO article)
			throws ModelException {
		checkPermission(AccessPermissions.ARTICLE_VIEW);
		IKnowledgeManagementFacade knowledge = Environment.getKnowledgeFacade();
		List<ArticleTO> similarArticles = knowledge.suggestSimilarArticlesView(article);
		return similarArticles;
	}

}
