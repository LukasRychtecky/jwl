package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.util.ArticleIdPair;

public interface IKnowledgeManagementFacade {
	public List<ArticleTO> suggestSimilarArticlesEdit(String tags, String name,
			String text);

	public void extractKeyWords();
	
	public List<String> extractKeyWordsOnRun(String title, String text);
	
	public List<ArticleIdPair> suggestArticleMerge();
	
	public void pregenerateMergeSuggestion();
	
	public List<ArticleIdPair> getPregeneratedMergeSuggestions() throws KnowledgeException;
	
	public List<ArticleTO> getKeyWordSearchResult(SearchTO searchData) throws KnowledgeException;
	
	public void addIgnoredMergeSuggestion(ArticleIdPair articleIdPair) throws KnowledgeException;
	
	public void cleanIgnoredMergeSuggestions();
	
	public double getLivabilityInitialValue();
	
	public void doLivabilityPeriodicReduction();
	
	public void handleArticleRatingLivability(ArticleId articleId, double rating);
	
	public void revertArticleRatingLivability(ArticleId articleId, double rating);
	
	public void addLivability(ArticleId articleId, double livability) throws KnowledgeException;
	
	public void handleArticleViewLivability(ArticleId articleId);
	
	public List<ArticleTO> suggestSimilarArticlesView(ArticleTO article);
	
}
