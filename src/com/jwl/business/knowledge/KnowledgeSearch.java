package com.jwl.business.knowledge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.KeyWordTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.keyword.WordProcessor;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.keyword.IKeyWordDAO;

public class KnowledgeSearch {

	IArticleDAO articleDAO;
	IKeyWordDAO keyWordDAO;
	ISettingsSource knowledgeSettings;

	public KnowledgeSearch(IArticleDAO articleDAO, IKeyWordDAO keyWordDAO,
			ISettingsSource knowledgeSettings) {
		this.articleDAO = articleDAO;
		this.keyWordDAO = keyWordDAO;
		this.knowledgeSettings = knowledgeSettings;
	}

	public List<ArticleTO> getSearchResult(String searchExpression)
			throws KnowledgeException {
		Set<String> searchWords = WordProcessor.getWords(searchExpression);
		searchWords = excludeNonKeyWords(searchWords);
		List<ArticleTO> articlesWithKW = null;
		try {
			articlesWithKW = articleDAO.findArticleWithKeyWord(searchWords);
		} catch (DAOException e) {
			throw new KnowledgeException();
		}
		return getBestMatching(articlesWithKW, searchWords);
	}

	private List<ArticleTO> getBestMatching(List<ArticleTO> articles,
			Set<String> keyWords) {
		Map<ArticleTO, Float> weightedArticles = new HashMap<ArticleTO, Float>();
		for (ArticleTO article : articles) {
			float weight = getWordWeight(article.getKeyWords(), keyWords);
			weightedArticles.put(article, weight);
		}
		return orderWordList(weightedArticles);
	}

	private float getWordWeight(List<KeyWordTO> keyWords,
			Set<String> searchWords) {
		float sum = 0;
		for (KeyWordTO kw : keyWords) {
			if (searchWords.contains(kw.getWord())) {
				sum += kw.getWeight();
			}
		}
		return sum;
	}

	private List<ArticleTO> orderWordList(Map<ArticleTO, Float> wordWeights) {
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
	
	private Set<String> excludeNonKeyWords(Set<String> searchWords) throws KnowledgeException{
		List<KeyWordTO> keyWords =null;
		try {
			keyWords=keyWordDAO.getAll();
		} catch (DAOException e) {
			throw new KnowledgeException(e);
		}
		Set<String> kws = new HashSet<String>();
		for(KeyWordTO kw :keyWords){
			kws.add(kw.getWord());
		}
		Set<String> searchWordsFiltered = new HashSet<String>();
		for(String searchWord:searchWords){
			if(kws.contains(searchWord)){
				searchWordsFiltered.add(searchWord);
			}
		}
		return searchWordsFiltered;
	}
}
