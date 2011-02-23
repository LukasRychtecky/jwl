package com.jwl.business.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwl.business.article.ArticleTO;

public class SimilarArticleSuggestor implements INeuronInput {
	private Neuron neuron;
	private ISettingsSource settingsSource;
	private Map<String, WeightRecord> neuronInput;
	private IArticleIterator articleFeeder;

	enum InputNames {
		NAME_SIMILARITY("NameSimilarity"), TAG_SIMILARITY("TagSimilarity"), AUTHOR_ROLE_SIMILARITY(
				"AuthorRoleSimilarity");

		public String name;

		private InputNames(String name) {
			this.name = name;
		}
	}

	public SimilarArticleSuggestor(IArticleIterator articleFeeder) {
		this.settingsSource = new SettingsSource();
		this.neuron = new Neuron(this, settingsSource, "articleSimilarity");
		this.articleFeeder = articleFeeder;
	}

	@Override
	public Map<String, WeightRecord> feedInput() {
		return neuronInput;
	}

	public List<ArticleTO> suggestSimilarArticles(String tags, String name) {
		List<ArticleTO> similarArticles = new ArrayList<ArticleTO>();
		while (articleFeeder.hasNext()) {
			
			ArticleTO comparedArticle = articleFeeder.getNextArticle();
			neuronInput = new HashMap<String, WeightRecord>();
			float nameSimilarity = getNameSimilarityRatio(name, comparedArticle);
			WeightRecord nsInputRecord = new WeightRecord(
					InputNames.NAME_SIMILARITY.name, nameSimilarity);
			neuronInput.put(nsInputRecord.getName(), nsInputRecord);
			float tagSimilarity = getTagSimilarityRatio(tags, comparedArticle);
			WeightRecord tsInputRecord = new WeightRecord(
					InputNames.TAG_SIMILARITY.name, tagSimilarity);
			neuronInput.put(tsInputRecord.getName(), tsInputRecord);
			float neuronOutput = neuron.getOutput();
			// if (neuronOutput != 0) {
			similarArticles.add(comparedArticle);
			// }
		}
		return similarArticles;
	}

	private float getNameSimilarityRatio(String name, ArticleTO comparedArticle) {
		List<String> constructedArticleNameWords = disassembleName(name);
		List<String> comparedArticleNameWords = disassembleName(comparedArticle
				.getTitle());
		return getWordRatio(constructedArticleNameWords,
				comparedArticleNameWords);
	}

	private float getTagSimilarityRatio(String tags, ArticleTO comparedArticle) {
		List<String> constructedArticleTags = disassembleName(tags);
		List<String> comparedArticleTags = new ArrayList<String>(
				comparedArticle.getTags());
		return getWordRatio(constructedArticleTags, comparedArticleTags);
	}

	private float getWordRatio(List<String> words1, List<String> words2) {
		int denominator;
		int numerator = 0;
		if (words1.size() == 0 || words2.size() == 0) {
			return 0;
		}
		if (words1.size() > words2.size()) {
			denominator = words2.size();
		} else {
			denominator = words1.size();
		}
		for (String word1 : words1) {
			for (String word2 : words2) {
				if (word1.equals(word2)) {
					numerator++;
					break;
				}
			}
		}
		float result = numerator;
		return result / denominator;
	}

	private List<String> disassembleName(String name) {
		List<String> words = new ArrayList<String>();
		name = name.trim();
		int lastSpace = 0;
		if (name == null || name.equals("")) {
			return words;
		}
		while (true) {
			int spacePos = name.indexOf(' ', lastSpace);
			if (spacePos == -1) {
				words.add(name.substring(lastSpace));
				break;
			}
			words.add(name.substring(lastSpace, spacePos));
			lastSpace = spacePos + 1;
		}
		return words;
	}
}
