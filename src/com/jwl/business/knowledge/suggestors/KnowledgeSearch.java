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
import com.jwl.business.article.SearchTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;
import com.jwl.business.knowledge.keyword.WordProcessor;
import com.jwl.business.knowledge.util.ArticleIterator;
import com.jwl.business.knowledge.util.IArticleIterator;
import com.jwl.business.knowledge.util.ISettingsSource;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.keyword.IKeyWordDAO;

public class KnowledgeSearch {

	IArticleDAO articleDAO;
	IKeyWordDAO keyWordDAO;
	ISettingsSource knowledgeSettings;
	private boolean searchInEditors;
	private boolean searchInTags;
	private boolean searchInKeyWords;
	private boolean searchInTitle;
	private Set<String> searchWords;
	private static final String featureName = "Search";
	

	private enum InputNames {
		KEY_WORDS("KeyWords"), TAGS("Tags"), EDITORS("Editors"), TITLE("Title"), RATING(
				"Rating"), UNMATCHED_TOLERANCE("UnmatchedWordsTolerance");

		public String name;

		private InputNames(String name) {
			this.name = name;
		}
	}

	public KnowledgeSearch(IArticleDAO articleDAO, IKeyWordDAO keyWordDAO,
			ISettingsSource knowledgeSettings) {
		this.articleDAO = articleDAO;
		this.keyWordDAO = keyWordDAO;
		this.knowledgeSettings = knowledgeSettings;
	}

	public List<ArticleTO> getSearchResult(SearchTO searchTO)
			throws KnowledgeException {
		setSearchCategories(searchTO);
		if (!isValidInput()) {
			return new ArrayList<ArticleTO>();
		}
		List<ArticleTO> result = null;
		try {
			result = getBestMatching();
		} catch (Exception e) {
			throw new KnowledgeException(e);
		}
		return result;
	}

	private List<ArticleTO> getBestMatching() throws DAOException, KnowledgeManagementSettingsException {
		IArticleIterator iterator = new ArticleIterator(articleDAO, 100);
		Map<ArticleTO, Double> articleWeights = new HashMap<ArticleTO, Double>();
		double benchmark = computeBenchmarkValue();
		while (iterator.hasNext()) {
			ArticleTO article = iterator.getNextArticle();
			processArticle(article, articleWeights, benchmark);
		}
		return orderWordList(articleWeights);		
	}

	private void processArticle(ArticleTO article, Map<ArticleTO, Double> articleWeights, double benchmark) throws KnowledgeManagementSettingsException, DAOException {
		Map<String, Boolean> matchMap = createWordMap();
		double keyWordMatchValue = processKeyWords(matchMap, article);
		int tagMatchCount = processTags(matchMap, article);
		int editorMatchCount = processEditors(matchMap, article);
		int titleMatchCount = processTitle(matchMap, article);
		Set<String> unmatchedWords = getUnmatchedWords(matchMap);
		if(!isUnmatchedWithinLimit(unmatchedWords.size(), searchWords.size())){
			return;
		}
		if(!isInFullTextSearch(unmatchedWords, article)){
			return;
		}
		
		double articleValue = computeKeyWordsValue(keyWordMatchValue);
		articleValue+=computeTagsValue(tagMatchCount, benchmark);
		articleValue+=computeTitleValue(titleMatchCount, benchmark);
		articleValue+=computeEditorsValue(editorMatchCount, benchmark);
		articleWeights.put(article, articleValue);
	}

	private double processKeyWords(Map<String, Boolean> matchMap, ArticleTO article) {
		double result = 0.0;
		if (!searchInKeyWords) {
			return result;
		}

		for (KeyWordTO kw : article.getKeyWords()) {
			if (searchWords.contains(kw.getWord())) {
				matchMap.put(kw.getWord(), true);
				result+=kw.getWeight();
			}
		}
		return result;
	}

	private int processTags(Map<String, Boolean> matchMap, ArticleTO article) {
		int result = 0;
		if (!searchInTags) {
			return result;
		}

		for (String tag : article.getTags()) {
			if (searchWords.contains(tag)) {
				matchMap.put(tag, true);
				result++;
			}
		}
		return result;
	}

	private int processTitle(Map<String, Boolean> matchMap, ArticleTO article) {
		int result = 0;
		if (!searchInTitle) {
			return result;
		}

		Set<String> titleWords = WordProcessor.getWords(article.getTitle());
		for (String word : titleWords) {
			if (searchWords.contains(word)) {
				matchMap.put(word, true);
				result++;
			}
		}
		return result;
	}

	private int processEditors(Map<String, Boolean> matchMap, ArticleTO article) {
		int result = 0;
		if (!searchInEditors) {
			return result;
		}

		Set<String> editorWords = WordProcessor.getWords(article.getEditor());
		for (String word : editorWords) {
			if (searchWords.contains(word)) {
				matchMap.put(word, true);
				result++;
			}
		}
		return result;
	}

	private Map<String, Boolean> createWordMap() {
		Map<String, Boolean> result = new HashMap<String, Boolean>(
				searchWords.size());
		for (String word : searchWords) {
			result.put(word, false);
		}
		return result;
	}

	private List<ArticleTO> orderWordList(Map<ArticleTO, Double> articleWeights) {
		List<Entry<ArticleTO, Double>> list = new LinkedList<Map.Entry<ArticleTO, Double>>(
				articleWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<ArticleTO, Double>>() {
			@Override
			public int compare(Entry<ArticleTO, Double> arg0,
					Entry<ArticleTO, Double> arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		List<ArticleTO> keyWordList = new ArrayList<ArticleTO>();
		for (int i = 0; i < list.size(); i++) {
			Entry<ArticleTO, Double> e = list.get(i);
			keyWordList.add(e.getKey());
		}
		return keyWordList;
	}

	private Set<String> getUnmatchedWords(Map<String, Boolean> wordMatch) {
		Set<String> result = new HashSet<String>();
		for (Entry<String, Boolean> e : wordMatch.entrySet()) {
			if (e.getValue().booleanValue() == false) {
				result.add(e.getKey());
			}
		}
		return result;
	}

	/*private Set<String> excludeNonKeyWords(Set<String> searchWords)
			throws KnowledgeException {
		List<KeyWordTO> keyWords = null;
		try {
			keyWords = keyWordDAO.getAll();
		} catch (DAOException e) {
			throw new KnowledgeException(e);
		}
		Set<String> kws = new HashSet<String>();
		for (KeyWordTO kw : keyWords) {
			kws.add(kw.getWord());
		}
		Set<String> searchWordsFiltered = new HashSet<String>();
		for (String searchWord : searchWords) {
			if (kws.contains(searchWord)) {
				searchWordsFiltered.add(searchWord);
			}
		}
		return searchWordsFiltered;
	}*/

	private void setSearchCategories(SearchTO searchTO) {
		if (searchTO.isTags()) {
			this.searchInTags = true;
		}
		if (searchTO.isEditors()) {
			this.searchInEditors = true;
		}
		if (searchTO.isKeyWords()) {
			this.searchInKeyWords = true;
		}
		if (searchTO.isTitle()) {
			this.searchInTitle = true;
		}
		searchWords = WordProcessor.getWords(searchTO.getSearchPhrase());
	}

	private boolean isValidInput() {
		if ((searchInTags || searchInEditors || searchInKeyWords || searchInTitle)
				&& !searchWords.isEmpty()) {
			return true;
		}
		return false;
	}
	
	private boolean isUnmatchedWithinLimit(int unmatchedSize, int searchSize) throws KnowledgeManagementSettingsException{
		double limitKoef = knowledgeSettings.getWeight(featureName, InputNames.UNMATCHED_TOLERANCE.name);
		if(unmatchedSize<=limitKoef*searchSize){
			return true;
		}
		return false;
	}
	
	private boolean isInFullTextSearch(Set<String> words, ArticleTO article){
		String articleText = article.getText();
		for(String word : words){
			if(!articleText.contains(word)){
				return false;
			}
		}
		return true;
	}
	
	private double computeKeyWordsValue(double keyWordsValue) throws KnowledgeManagementSettingsException{
		double weight = knowledgeSettings.getWeight(featureName, InputNames.KEY_WORDS.name);
		return weight * keyWordsValue;
	}
	
	private double computeTagsValue(int tagMatchCount, double benchmark) throws KnowledgeManagementSettingsException{
		double weight = knowledgeSettings.getWeight(featureName, InputNames.TAGS.name);
		return weight*benchmark*tagMatchCount;
	}
	
	private double computeTitleValue(int tagMatchCount, double benchmark) throws KnowledgeManagementSettingsException{
		double weight = knowledgeSettings.getWeight(featureName, InputNames.TITLE.name);
		return weight*benchmark*tagMatchCount;
	}
	
	private double computeEditorsValue(int tagMatchCount, double benchmark) throws KnowledgeManagementSettingsException{
		double weight = knowledgeSettings.getWeight(featureName, InputNames.EDITORS.name);
		return weight*benchmark*tagMatchCount;
	}
	
	private double computeBenchmarkValue() throws DAOException{
		IArticleIterator iterator = new ArticleIterator(articleDAO, 100);
		double result = 0.0;
		int count =0;
		while(iterator.hasNext()){
			ArticleTO article = iterator.getNextArticle();
			List<KeyWordTO> kws =article.getKeyWords();
			if(!kws.isEmpty()){
				result+=kws.get(0).getWeight();
				count++;
			}		
		}
		return result/count;
	}
}
