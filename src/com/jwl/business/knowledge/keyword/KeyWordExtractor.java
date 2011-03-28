package com.jwl.business.knowledge.keyword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.KeyWordTO;
import com.jwl.business.knowledge.util.ArticleIterator;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.keyword.IKeyWordDAO;
import com.jwl.util.markdown.MarkdownRemover;

public class KeyWordExtractor {
	private IArticleDAO articleDAO;
	private IKeyWordDAO keyWordDAO;
	private Map<String, Integer> wordsInArticles;
	private static int TAG_COUNT = 10;

	public KeyWordExtractor(IArticleDAO articleDAO, IKeyWordDAO keyWordDAO) {
		this.articleDAO = articleDAO;
		this.keyWordDAO = keyWordDAO;
	}

	public void extractKeyWords() {
		try {
			countWordsInArticles();
		} catch (DAOException e) {
		}
		WordCountsFileManager fileManager = new WordCountsFileManager();
		fileManager.saveToFile(wordsInArticles);
		ArticleIterator articleIterator = new ArticleIterator(articleDAO, 100);
		try {
			while (articleIterator.hasNext()) {
				ArticleTO article = articleIterator.getNextArticle();
				processArticle(article);
			}
		} catch (DAOException e) {
		}
	}

	public List<String> extractKeyWordsOnRun(String title, String text) {
		WordCountsFileManager fileManager = new WordCountsFileManager();
		wordsInArticles = fileManager.getFromFile();
		Map<String, Float> keyWordsWeights = computeKeyWordWeights(title, text);
		List<String> keyWordList = createKeyWordList(keyWordsWeights);
		return keyWordList;
	}

	private void countWordsInArticles() throws DAOException {
		wordsInArticles = new HashMap<String, Integer>();
		MarkdownRemover mr = new MarkdownRemover();
		ArticleIterator articleIterator = new ArticleIterator(articleDAO, 100);
		while (articleIterator.hasNext()) {
			ArticleTO article = articleIterator.getNextArticle();
			Set<String> titleWords = WordProcessor.getWordsCountInString(
					article.getTitle()).keySet();
			insertWordsInMap(wordsInArticles, titleWords);
			String articleText = mr.removeMarkdown(article.getText());
			Set<String> textWords = WordProcessor.getWordsCountInString(
					articleText).keySet();
			insertWordsInMap(wordsInArticles, textWords);
		}
	}

	private void insertWordsInMap(Map<String, Integer> map, Set<String> words) {
		for (String word : words) {
			if (map.containsKey(word)) {
				map.put(word, map.get(word).intValue() + 1);
			} else {
				map.put(word, 1);
			}
		}
	}

	private float computeTFIDF(String word, int articleOccur, int articleLenght) {
		int articleSum = 0;
		try {
			articleSum = articleDAO.getCount();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float tf = (float) articleOccur / articleLenght;
		float idf = (float) Math.log((double) articleSum
				/ wordsInArticles.get(word));
		return tf * idf;
	}

	private void processArticle(ArticleTO article) {
		Map<String, Float> wordWeights = computeKeyWordWeights(
				article.getTitle(), article.getText());
		try {
			keyWordDAO.deleteAll(article.getId());
			List<KeyWordTO> keyWords = createKeyWords(wordWeights);
			keyWordDAO.create(keyWords, article.getId());
		} catch (DAOException e) {

		}
	}

	private Map<String, Float> computeKeyWordWeights(String title, String text) {
		Map<String, Integer> wordCounts = new HashMap<String, Integer>();
		MarkdownRemover mr = new MarkdownRemover();
		title = mr.removeMarkdown(title);
		int titleWordNum = WordProcessor.getWordNumber(title);
		Map<String, Integer> titleWords = WordProcessor
				.getWordsCountInString(title);
		insertWordsInMap(wordCounts, titleWords);
		text = mr.removeMarkdown(text);
		int textWordNum = WordProcessor.getWordNumber(text);
		Map<String, Integer> textWords = WordProcessor
				.getWordsCountInString(text);
		insertWordsInMap(wordCounts, textWords);

		Map<String, Float> wordWeights = new HashMap<String, Float>();
		for (Entry<String, Integer> wc : wordCounts.entrySet()) {
			if (wordsInArticles.containsKey(wc.getKey())) {
				float weight = computeTFIDF(wc.getKey(), wc.getValue(),
						titleWordNum + textWordNum);
				wordWeights.put(wc.getKey(), weight);
			}
		}
		return wordWeights;
	}

	private void insertWordsInMap(Map<String, Integer> map,
			Map<String, Integer> words) {
		for (Entry<String, Integer> word : words.entrySet()) {
			if (map.containsKey(word)) {
				map.put(word.getKey(), map.get(word) + word.getValue());
			} else {
				map.put(word.getKey(), word.getValue());
			}
		}
	}

	private List<KeyWordTO> createKeyWords(Map<String, Float> wordWeights) {
		List<Entry<String, Float>> list = new LinkedList<Map.Entry<String, Float>>(
				wordWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Float>>() {
			@Override
			public int compare(Entry<String, Float> arg0,
					Entry<String, Float> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});

		List<KeyWordTO> keyWords = new ArrayList<KeyWordTO>();
		for (int i = 0; i < TAG_COUNT && i < list.size(); i++) {
			Entry<String, Float> e = list.get(i);
			KeyWordTO kw = createKeyWord(e.getKey(), e.getValue());
			keyWords.add(kw);
		}
		return keyWords;
	}

	private List<String> createKeyWordList(Map<String, Float> wordWeights) {
		List<Entry<String, Float>> list = new LinkedList<Map.Entry<String, Float>>(
				wordWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Float>>() {
			@Override
			public int compare(Entry<String, Float> arg0,
					Entry<String, Float> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		List<String> keyWordList = new ArrayList<String>();
		for (int i = 0; i < TAG_COUNT && i < list.size(); i++) {
			Entry<String, Float> e = list.get(i);
			keyWordList.add(e.getKey());
		}
		return keyWordList;
	}

	private KeyWordTO createKeyWord(String word, float weight) {
		KeyWordTO keyWord = new KeyWordTO();
		keyWord.setWord(word);
		keyWord.setWeight((double) weight);
		keyWord.setCreated(new Date());
		return keyWord;
	}

}
