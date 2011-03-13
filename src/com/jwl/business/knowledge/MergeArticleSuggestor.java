package com.jwl.business.knowledge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.keyword.WordProcessor;
import com.jwl.integration.article.IArticleDAO;

public class MergeArticleSuggestor extends AbstractArticleSuggestor {

	private static final String MERGE_FILEPATH = "C:\\JWL_BW\\SeamWiki\\resources\\merge.txt";
	private static final String MERGE_IGNORED_FILEPATH = "C:\\JWL_BW\\SeamWiki\\resources\\ignoredmerge";

	public MergeArticleSuggestor(IArticleDAO articleDAO,
			ISettingsSource knowledgeSettings) {
		super(articleDAO, knowledgeSettings);
		this.neuron = new Neuron(settingsSource, "MergeSuggestion");
	}

	public List<ArticleIdPair> suggestArticleMerge() {
		Map<ArticleIdPair, Float> articleIdPairs = new HashMap<ArticleIdPair, Float>();
		IArticleIterator iterator1 = new ArticleIterator(articleDAO, 50);
		IArticleIterator iterator2 = new ArticleIterator(articleDAO, 50);
		List<ArticleIdPair> ignoredPairs = null;
		try {
			ignoredPairs = getIdPairsFromFile(MERGE_IGNORED_FILEPATH);
		} catch (KnowledgeException e1) {

		}
		try {
			while (iterator1.hasNext()) {
				ArticleTO article1 = iterator1.getNextArticle();
				Set<String> a1NameWords = WordProcessor.getWords(article1
						.getTitle());
				KnowledgeManagementFacade f = new KnowledgeManagementFacade();
				Set<String> a1KeyWords = new HashSet<String>(
						f.extractKeyWordsOnRun(article1.getTitle(),
								article1.getText()));
				while (iterator2.hasNext()) {
					ArticleTO article2 = iterator2.getNextArticle();
					ArticleIdPair aip = new ArticleIdPair(article1.getId(),
							article2.getId());
					if (article1.getId().getId()
							.equals(article2.getId().getId())
							|| articleIdPairs.containsKey(aip)
							|| ignoredPairs.contains(aip)) {
						continue;
					}
					Map<String, WeightRecord> neuronInput = processArticle(
							a1NameWords, article1.getTags(), a1KeyWords,
							article2);
					float neuronOutput = neuron.getOutput(neuronInput);
					if (neuronOutput != 0) {
						articleIdPairs.put(new ArticleIdPair(article1.getId(),
								article2.getId()), neuronOutput);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}

		return createOrderedPairList(articleIdPairs);
	}

	public void pregenerateMergeSuggestion() throws KnowledgeException {
		List<ArticleIdPair> suggestedMerge = suggestArticleMerge();
		saveIdPairsToFile(suggestedMerge, MERGE_FILEPATH);
	}

	public List<ArticleIdPair> getPregeneratedMergeSuggestions()
			throws KnowledgeException {
		List<ArticleIdPair> mergeSuggestions = getIdPairsFromFile(MERGE_FILEPATH);
		return mergeSuggestions;
	}

	private List<ArticleIdPair> createOrderedPairList(
			Map<ArticleIdPair, Float> wordWeights) {
		List<Entry<ArticleIdPair, Float>> list = new LinkedList<Map.Entry<ArticleIdPair, Float>>(
				wordWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<ArticleIdPair, Float>>() {
			@Override
			public int compare(Entry<ArticleIdPair, Float> arg0,
					Entry<ArticleIdPair, Float> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		List<ArticleIdPair> result = new ArrayList<ArticleIdPair>();
		for (Entry<ArticleIdPair, Float> e : list) {
			result.add(e.getKey());
		}
		return result;
	}

	public void addIgnoredMergeSuggestion(ArticleIdPair articleIdPair)
			throws KnowledgeException {
		File f = new File(MERGE_IGNORED_FILEPATH);
		List<ArticleIdPair> ignoredIdPairs = null;
		if (f.exists()) {
			ignoredIdPairs = getIdPairsFromFile(MERGE_IGNORED_FILEPATH);
		} else {
			ignoredIdPairs = new ArrayList<ArticleIdPair>();
		}
		ignoredIdPairs.add(articleIdPair);
		saveIdPairsToFile(ignoredIdPairs, MERGE_IGNORED_FILEPATH);
	}

	public void cleanIgnoredMergeSuggestions() {
		List<ArticleIdPair> ignoredPairs = null;
		try {
			ignoredPairs = getIdPairsFromFile(MERGE_IGNORED_FILEPATH);
		} catch (KnowledgeException e) {
			return;
		}
		try {
			for (ArticleIdPair idPair : ignoredPairs) {
				ArticleTO a1 = articleDAO.get(idPair.getId1());
				ArticleTO a2 = articleDAO.get(idPair.getId2());
				if (a1 == null || a2 == null) {
					ignoredPairs.remove(idPair);
				}
			}
			saveIdPairsToFile(ignoredPairs, MERGE_IGNORED_FILEPATH);
		} catch (Exception e) {
		}
	}

	private void saveIdPairsToFile(List<ArticleIdPair> idPairs, String filePath)
			throws KnowledgeException {
		try {
			File f = new File(filePath);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(idPairs);
			out.close();
		} catch (IOException e) {
			throw new KnowledgeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private List<ArticleIdPair> getIdPairsFromFile(String filePath)
			throws KnowledgeException {
		List<ArticleIdPair> result = null;
		try {
			File f = new File(filePath);
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream in = new ObjectInputStream(fis);
			result = (List<ArticleIdPair>) in.readObject();
		} catch (Exception e) {
			throw new KnowledgeException(e);
		}
		return result;
	}
}
