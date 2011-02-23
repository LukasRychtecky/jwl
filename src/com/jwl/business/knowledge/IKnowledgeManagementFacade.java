package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.article.ArticleTO;

public interface IKnowledgeManagementFacade {
	public List<ArticleTO> suggestSimilarArticles(String tags, String name);
}
