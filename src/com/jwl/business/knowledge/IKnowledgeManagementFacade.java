package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;

public interface IKnowledgeManagementFacade {
	public List<ArticleTO> suggestSimilarArticles(String tags, String name,
			String text);

	public void extractKeyWords();
	
	public List<String> extractKeyWordsOnRun(String title, String text);
	
	public List<ArticleIdPair> suggestArticleMerge();
	
	public void pregenerateMergeSuggestion();
	
	public List<ArticleIdPair> getPregeneratedMergeSuggestions() throws KnowledgeException;
	
	public List<ArticleTO> getKeyWordSearchResult(String searchExpression) throws KnowledgeException;
	
	public void addIgnoredMergeSuggestion(ArticleIdPair articleIdPair) throws KnowledgeException;
	
	public void cleanIgnoredMergeSuggestions();
	
}
