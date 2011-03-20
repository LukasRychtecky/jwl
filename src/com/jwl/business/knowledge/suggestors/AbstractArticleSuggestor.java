package com.jwl.business.knowledge.suggestors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.KeyWordTO;
import com.jwl.business.knowledge.keyword.WordProcessor;
import com.jwl.business.knowledge.util.ISettingsSource;
import com.jwl.business.knowledge.util.Neuron;
import com.jwl.business.knowledge.util.WeightRecord;
import com.jwl.integration.article.IArticleDAO;

public class AbstractArticleSuggestor {

	protected Neuron neuron;
	protected ISettingsSource settingsSource;
	protected IArticleDAO articleDAO;

	enum InputNames {
		NAME_SIMILARITY("NameSimilarity"), TAG_SIMILARITY("TagSimilarity"), AUTHOR_ROLE_SIMILARITY(
				"AuthorRoleSimilarity"), KEY_WORD_SIMILARITY(
				"KeyWordSimilarity");

		public String name;

		private InputNames(String name) {
			this.name = name;
		}
	}

	public AbstractArticleSuggestor(IArticleDAO articleDAO,
			ISettingsSource knowledgeSettings) {
		this.settingsSource = knowledgeSettings;
		this.articleDAO = articleDAO;
	}

	protected Map<String, WeightRecord> processArticle(
			Set<String> constructedArticleNameWords,
			Set<String> constructedArticleTags,
			Set<String> constructedArticleKeyWords, ArticleTO comparedArticle) {
		Map<String, WeightRecord> neuronInput = new HashMap<String, WeightRecord>();
		float nameSimilarity = getNameSimilarityRatio(
				constructedArticleNameWords, comparedArticle);
		WeightRecord nsInputRecord = new WeightRecord(
				InputNames.NAME_SIMILARITY.name, nameSimilarity);
		neuronInput.put(nsInputRecord.getName(), nsInputRecord);
		float tagSimilarity = getTagSimilarityRatio(constructedArticleTags,
				comparedArticle);
		WeightRecord tsInputRecord = new WeightRecord(
				InputNames.TAG_SIMILARITY.name, tagSimilarity);
		neuronInput.put(tsInputRecord.getName(), tsInputRecord);
		float kwSimilarity = getKeyWordSimilarityRatio(
				constructedArticleKeyWords, comparedArticle);
		WeightRecord kwInputRecord = new WeightRecord(
				InputNames.KEY_WORD_SIMILARITY.name, kwSimilarity);
		neuronInput.put(kwInputRecord.getName(), kwInputRecord);
		return neuronInput;
	}

	protected float getNameSimilarityRatio(
			Set<String> constructedArticleNameWords, ArticleTO comparedArticle) {
		Set<String> comparedArticleNameWords = WordProcessor
				.getWords(comparedArticle.getTitle());
		return WordProcessor.getWordsSimilarityRatio(
				constructedArticleNameWords, comparedArticleNameWords);
	}

	protected float getTagSimilarityRatio(Set<String> constructedArticleTags,
			ArticleTO comparedArticle) {
		Set<String> comparedArticleTags = comparedArticle.getTags();
		return WordProcessor.getWordsSimilarityRatio(constructedArticleTags,
				comparedArticleTags);
	}

	protected float getKeyWordSimilarityRatio(
			Set<String> constructedArticleKeyWords, ArticleTO comparedArticle) {
		Set<String> comparedArticleKeyWords = new HashSet<String>();
		for (KeyWordTO kwt : comparedArticle.getKeyWords()) {
			comparedArticleKeyWords.add(kwt.getWord());
		}
		return WordProcessor.getWordsSimilarityRatio(
				constructedArticleKeyWords, comparedArticleKeyWords);
	}

	protected List<ArticleTO> orderWordList(Map<ArticleTO, Float> wordWeights) {
		List<Entry<ArticleTO, Float>> list = new LinkedList<Map.Entry<ArticleTO, Float>>(
				wordWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<ArticleTO, Float>>() {
			@Override
			public int compare(Entry<ArticleTO, Float> arg0,
					Entry<ArticleTO, Float> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		List<ArticleTO> keyWordList = new ArrayList<ArticleTO>();
		for (int i = 0; i < list.size(); i++) {
			Entry<ArticleTO, Float> e = list.get(i);
			keyWordList.add(e.getKey());
		}
		return keyWordList;
	}

}