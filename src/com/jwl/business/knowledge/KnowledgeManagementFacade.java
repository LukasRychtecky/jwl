package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.dao.ArticleDAO;

public class KnowledgeManagementFacade implements IKnowledgeManagementFacade{

	SimilarArticleSuggestor sas;
	public KnowledgeManagementFacade(){
		sas = new SimilarArticleSuggestor(new ArticleIterator(new ArticleDAO(), 100));
	}
	
	@Override
	public List<ArticleTO> suggestSimilarArticles(String tags, String name) {
		return sas.suggestSimilarArticles(tags, name);
	}

}
