package com.jwl.business.knowledge;

import java.util.List;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.keyword.KeyWordExtractor;
import com.jwl.business.knowledge.suggestors.EditArticleSuggestor;
import com.jwl.business.knowledge.suggestors.KnowledgeSearch;
import com.jwl.business.knowledge.suggestors.LivablilityManager;
import com.jwl.business.knowledge.suggestors.MergeArticleSuggestor;
import com.jwl.business.knowledge.suggestors.ViewArticleSuggestor;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.keyword.IKeyWordDAO;

public class KnowledgeManagementFacade implements IKnowledgeManagementFacade {

	@Override
	public List<ArticleTO> suggestSimilarArticlesEdit(String tags, String name,
			String text) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		EditArticleSuggestor sas = new EditArticleSuggestor(adao,
				Environment.getKnowledgeSettings());
		return sas.suggestSimilarArticles(tags, name, text);
	}

	@Override
	public void extractKeyWords() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		IKeyWordDAO kwdao = Environment.getDAOFactory().getKeyWordDAO();
		KeyWordExtractor kwe = new KeyWordExtractor(adao, kwdao, Environment.getKnowledgeSettings());
		kwe.extractKeyWords();
	}

	@Override
	public List<String> extractKeyWordsOnRun(String title, String text) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		IKeyWordDAO kwdao = Environment.getDAOFactory().getKeyWordDAO();
		KeyWordExtractor kwe = new KeyWordExtractor(adao, kwdao, Environment.getKnowledgeSettings());
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
	public List<ArticleTO> getKeyWordSearchResult(SearchTO searchData)
			throws KnowledgeException {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		IKeyWordDAO kwdao = Environment.getDAOFactory().getKeyWordDAO();
		KnowledgeSearch ks = new KnowledgeSearch(adao, kwdao, Environment.getKnowledgeSettings());
		List<ArticleTO> searchResult = null;
		searchResult = ks.getSearchResult(searchData);
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

	@Override
	public double getLivabilityInitialValue() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		LivablilityManager lm = new LivablilityManager(adao, Environment.getKnowledgeSettings());
		return lm.getInitialValue();
	}

	@Override
	public void doLivabilityPeriodicReduction() {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		LivablilityManager lm = new LivablilityManager(adao, Environment.getKnowledgeSettings());
		lm.doPeriodicReduction();
	}

	@Override
	public void handleArticleRatingLivability(ArticleId articleId, double rating) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		LivablilityManager lm = new LivablilityManager(adao, Environment.getKnowledgeSettings());
		lm.handleArticleRating(articleId, rating);
	}

	@Override
	public void revertArticleRatingLivability(ArticleId articleId, double rating) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		LivablilityManager lm = new LivablilityManager(adao, Environment.getKnowledgeSettings());
		lm.revertArticleRating(articleId, rating);	
	}

	@Override
	public void addLivability(ArticleId articleId, double livability) throws KnowledgeException {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		LivablilityManager lm = new LivablilityManager(adao, Environment.getKnowledgeSettings());
		lm.addLivability(articleId, livability);	
	}

	@Override
	public void handleArticleViewLivability(ArticleId articleId) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		LivablilityManager lm = new LivablilityManager(adao, Environment.getKnowledgeSettings());
		lm.handleArticleView(articleId);		
	}

	@Override
	public List<ArticleTO> suggestSimilarArticlesView(
			ArticleTO article) {
		IArticleDAO adao = Environment.getDAOFactory().getArticleDAO();
		ViewArticleSuggestor vas = new ViewArticleSuggestor(adao,
				Environment.getKnowledgeSettings());
		return vas.suggestSimilarArticles(article);
	}

}
