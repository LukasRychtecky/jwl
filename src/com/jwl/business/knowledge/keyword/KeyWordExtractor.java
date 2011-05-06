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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.KeyWordTO;
import com.jwl.business.knowledge.util.ArticleIterator;
import com.jwl.business.knowledge.util.ISettings;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.keyword.IKeyWordDAO;
import com.jwl.presentation.markdown.MarkdownRemover;

public class KeyWordExtractor {
	private IArticleDAO articleDAO;
	private IKeyWordDAO keyWordDAO;
	private Map<String, Integer> wordsInArticles;
	private int keyWordNumber;
	private ISettings settings;
	private int articleCount;

	public KeyWordExtractor(IArticleDAO articleDAO, IKeyWordDAO keyWordDAO, ISettings settings) {
		this.articleDAO = articleDAO;
		this.keyWordDAO = keyWordDAO;
		this.settings = settings;
		keyWordNumber = settings.getKeyWordNumber();
	}

	public void extractKeyWords(){
		try{
			countWordsInArticles();
		}catch(DAOException e){
			Logger.getLogger(KeyWordExtractor.class.getName()).log(Level.SEVERE, "Key word extraction failed.");
			return;
		}
		WordCountsFileManager fileManager = new WordCountsFileManager(settings);
		fileManager.saveToFile(wordsInArticles);
		
		try{
			articleCount = articleDAO.getCount();
		}catch(DAOException e1){
			Logger.getLogger(KeyWordExtractor.class.getName()).log(Level.SEVERE, "Key word extraction failed.");
			return;
		}
		
		ArticleIterator articleIterator = new ArticleIterator(articleDAO, 100);
		try{
			while(articleIterator.hasNext()){
				ArticleTO article = articleIterator.getNextArticle();
				processArticle(article);
			}
		}catch(DAOException e){
			Logger.getLogger(KeyWordExtractor.class.getName()).log(Level.SEVERE, "Key word extraction failed.");
			return;
		}
	}

	public Map<String, Float> extractKeyWordsOnRun(String title, String text){
		WordCountsFileManager fileManager = new WordCountsFileManager(settings);
		wordsInArticles = fileManager.getFromFile();
		Map<String, Float> keyWordsWeights = computeKeyWordWeights(title, text);
		return keyWordsWeights;
	}

	private void countWordsInArticles() throws DAOException{
		wordsInArticles = new HashMap<String, Integer>();
		MarkdownRemover mr = new MarkdownRemover();
		ArticleIterator articleIterator = new ArticleIterator(articleDAO, 100);
		while(articleIterator.hasNext()){
			ArticleTO article = articleIterator.getNextArticle();
			Set<String> titleWords = WordProcessor.getWordsCountInString(
					article.getTitle(), settings.getUsePorterStamer(), settings.getStopWordSetPath()).keySet();
			insertWordsInMap(wordsInArticles, titleWords);
			String articleText = mr.removeMarkdown(article.getText());
			Set<String> textWords = WordProcessor.getWordsCountInString(
					articleText, settings.getUsePorterStamer(), settings.getStopWordSetPath()).keySet();
			insertWordsInMap(wordsInArticles, textWords);
		}
	}

	private void insertWordsInMap(Map<String, Integer> map, Set<String> words){
		for (String word : words){
			if(map.containsKey(word)){
				map.put(word, map.get(word).intValue() + 1);
			}else{
				map.put(word, 1);
			}
		}
	}

	

	private void processArticle(ArticleTO article){
		Map<String, Float> wordWeights = computeKeyWordWeights(
				article.getTitle(), article.getText());
		try{
			keyWordDAO.deleteAll(article.getId());
			List<KeyWordTO> keyWords = createKeyWords(wordWeights);
			keyWordDAO.create(keyWords, article.getId());
		}catch(DAOException e){

		}
	}

	private Map<String, Float> computeKeyWordWeights(String title, String text){
		Map<String, Integer> wordCounts = new HashMap<String, Integer>();
		MarkdownRemover mr = new MarkdownRemover();
		title = mr.removeMarkdown(title);
		Map<String, Integer> titleWords = WordProcessor
				.getWordsCountInString(title, settings.getUsePorterStamer(), settings.getStopWordSetPath());
		insertWordsInMap(wordCounts, titleWords);
		text = mr.removeMarkdown(text);
		Map<String, Integer> textWords = WordProcessor
				.getWordsCountInString(text, settings.getUsePorterStamer(), settings.getStopWordSetPath());
		insertWordsInMap(wordCounts, textWords);
		Tfidf tfidf = new Tfidf(wordsInArticles, articleCount);
		return tfidf.computeArticleWordWeights(wordCounts);	
	}

	private void insertWordsInMap(Map<String, Integer> map,
			Map<String, Integer> words){
		for (Entry<String, Integer> word : words.entrySet()){
			if(map.containsKey(word)){
				map.put(word.getKey(), map.get(word) + word.getValue());
			}else{
				map.put(word.getKey(), word.getValue());
			}
		}
	}

	private List<KeyWordTO> createKeyWords(Map<String, Float> wordWeights){
		List<Entry<String, Float>> list = new LinkedList<Map.Entry<String, Float>>(
				wordWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Float>>() {
			@Override
			public int compare(Entry<String, Float> arg0,
					Entry<String, Float> arg1){
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});

		List<KeyWordTO> keyWords = new ArrayList<KeyWordTO>();
		for (int i = 0; i < keyWordNumber && i < list.size(); i++){
			Entry<String, Float> e = list.get(i);
			KeyWordTO kw = createKeyWord(e.getKey(), e.getValue());
			keyWords.add(kw);
		}
		return keyWords;
	}

	private KeyWordTO createKeyWord(String word, float weight){
		KeyWordTO keyWord = new KeyWordTO();
		keyWord.setWord(word);
		keyWord.setWeight((double) weight);
		keyWord.setCreated(new Date());
		return keyWord;
	}

}
