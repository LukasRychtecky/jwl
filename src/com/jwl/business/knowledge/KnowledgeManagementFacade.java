package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.keyword.KeyWordExtractor;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.keyword.IKeyWordDAO;

public class KnowledgeManagementFacade implements IKnowledgeManagementFacade {

	@Override
	public List<ArticleTO> suggestSimilarArticles(String tags, String name,
			String text) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		SimilarArticleSuggestor sas = new SimilarArticleSuggestor(adao,
				Environment.getKnowledgeSettings());
		return sas.suggestSimilarArticles(tags, name, text);
	}

	@Override
	public void extractKeyWords() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		IKeyWordDAO kwdao = Environment.getDAOFactory().getKeyWordDAO();
		KeyWordExtractor kwe = new KeyWordExtractor(adao, kwdao);
		kwe.extractKeyWords();
	}

	@Override
	public List<String> extractKeyWordsOnRun(String title, String text) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		IKeyWordDAO kwdao = Environment.getDAOFactory().getKeyWordDAO();
		KeyWordExtractor kwe = new KeyWordExtractor(adao, kwdao);
		return kwe.extractKeyWordsOnRun(title, text);
	}

	@Override
	public List<ArticleIdPair> suggestArticleMerge() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		MergeArticleSuggestor ams = new MergeArticleSuggestor(adao, Environment.getKnowledgeSettings());
		return ams.suggestArticleMerge();
	}

	@Override
	public void pregenerateMergeSuggestion() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		MergeArticleSuggestor ams = new MergeArticleSuggestor(adao, Environment.getKnowledgeSettings());
		try {
			ams.pregenerateMergeSuggestion();
		} catch (KnowledgeException e) {
		}		
	}

	@Override
	public List<ArticleIdPair> getPregeneratedMergeSuggestions() throws KnowledgeException{
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		MergeArticleSuggestor ams = new MergeArticleSuggestor(adao, Environment.getKnowledgeSettings());
		List<ArticleIdPair> mergeSuggestion =null;
		mergeSuggestion = ams.getPregeneratedMergeSuggestions();
		return mergeSuggestion;
	}

	@Override
	public List<ArticleTO> getKeyWordSearchResult(String searchExpression)
			throws KnowledgeException {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		IKeyWordDAO kwdao = Environment.getDAOFactory().getKeyWordDAO();
		KnowledgeSearch ks = new KnowledgeSearch(adao, kwdao, Environment.getKnowledgeSettings());
		List<ArticleTO> searchResult = null;
		searchResult = ks.getSearchResult(searchExpression);
		return searchResult;		
	}

	@Override
	public void addIgnoredMergeSuggestion(ArticleIdPair articleIdPair)
			throws KnowledgeException {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		MergeArticleSuggestor ams = new MergeArticleSuggestor(adao, Environment.getKnowledgeSettings());
		ams.addIgnoredMergeSuggestion(articleIdPair);		
	}

	@Override
	public void cleanIgnoredMergeSuggestions() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		MergeArticleSuggestor ams = new MergeArticleSuggestor(adao, Environment.getKnowledgeSettings());
		ams.cleanIgnoredMergeSuggestions();
	}

}
