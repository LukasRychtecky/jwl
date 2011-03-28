package com.jwl.business.knowledge.suggestors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.knowledge.KnowledgeManagementFacade;
import com.jwl.business.knowledge.keyword.WordProcessor;
import com.jwl.business.knowledge.util.ArticleIterator;
import com.jwl.business.knowledge.util.IArticleIterator;
import com.jwl.business.knowledge.util.ISettingsSource;
import com.jwl.business.knowledge.util.Neuron;
import com.jwl.business.knowledge.util.WeightRecord;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;

public class ViewArticleSuggestor extends AbstractArticleSuggestor {

	public ViewArticleSuggestor(IArticleDAO articleDAO,
			ISettingsSource knowledgeSettings) {
		super(articleDAO, knowledgeSettings);
		this.neuron = new Neuron(settingsSource, "ArticleSimilarityView");
	}
	
	public List<ArticleTO> suggestSimilarArticles(ArticleTO article) {
		Map<ArticleTO, Float> articleWeights = new HashMap<ArticleTO, Float>();
		IArticleIterator articleFeeder = new ArticleIterator(articleDAO, 100);
		Set<String> constructedArticleNameWords = WordProcessor.getWords(article.getTitle());
		Set<String> constructedArticleTags = article.getTags();
		KnowledgeManagementFacade f = new KnowledgeManagementFacade();
		Set<String> constructedArticleKeyWords = new HashSet<String>(
				f.extractKeyWordsOnRun(article.getTitle(), article.getText()));
		while (articleFeeder.hasNext()) {
			ArticleTO comparedArticle;
			try {
				comparedArticle = articleFeeder.getNextArticle();
			} catch (DAOException e) {
				break;
			}
			Map<String, WeightRecord> neuronInput = processArticle(
					constructedArticleNameWords, constructedArticleTags,
					constructedArticleKeyWords, comparedArticle);
			float neuronOutput = neuron.getOutput(neuronInput);
			if (neuronOutput != 0 && !comparedArticle.getId().equals(article.getId())) {
				articleWeights.put(comparedArticle, neuronOutput);
			}
		}
		List<ArticleTO> similarArticles = orderWordList(articleWeights);
		return similarArticles;
	}

}
