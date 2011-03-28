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

public class EditArticleSuggestor extends AbstractArticleSuggestor {

	public EditArticleSuggestor(IArticleDAO articleDAO,
			ISettingsSource knowledgeSettings) {
		super(articleDAO, knowledgeSettings);
		this.neuron = new Neuron(settingsSource, "ArticleSimilarityEdit");
	}

	public List<ArticleTO> suggestSimilarArticles(String tags, String name,
			String text) {
		Map<ArticleTO, Float> articleWeights = new HashMap<ArticleTO, Float>();
		IArticleIterator articleFeeder = new ArticleIterator(articleDAO, 100);
		Set<String> constructedArticleNameWords = WordProcessor.getWords(name);
		Set<String> constructedArticleTags = WordProcessor.getWords(tags);
		KnowledgeManagementFacade f = new KnowledgeManagementFacade();
		Set<String> constructedArticleKeyWords = new HashSet<String>(
				f.extractKeyWordsOnRun(name, text));
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
			if (neuronOutput != 0) {
				articleWeights.put(comparedArticle, neuronOutput);
			}
		}
		List<ArticleTO> similarArticles = orderWordList(articleWeights);
		return similarArticles;
	}

}
