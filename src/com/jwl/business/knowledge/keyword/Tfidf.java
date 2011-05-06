package com.jwl.business.knowledge.keyword;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Tfidf {
	private Map<String, Integer> wordsInArticles;
	private int articleCount;
	
	
	
	public Tfidf(Map<String, Integer> wordsInArticles, int articleCount) {
		this.wordsInArticles = wordsInArticles;
		this.articleCount = articleCount;
	}

	public Map<String, Float> computeArticleWordWeights(Map<String, Integer> wordCounts){
		Map<String, Float> wordWeights = new HashMap<String, Float>();
		
		int maxTF = computeMaxTF(wordCounts);
		for (Entry<String, Integer> wc : wordCounts.entrySet()){
			if(wordsInArticles.containsKey(wc.getKey())){
				float weight = computeWordWeight(wc.getKey(), wc.getValue(), maxTF);
				wordWeights.put(wc.getKey(), weight);
			}
		}
		return wordWeights;
	}
	
	private float computeWordWeight(String word, int articleOccur, int maxTF){
		float tf = computeTF(articleOccur, maxTF);
		float idf = computeIDF(word);
		return tf * idf;
	}
	
	private float computeTF(int articleOccur, int maxTF){
		return (float) articleOccur / maxTF;
	}
	
	private float computeIDF(String word){
		 return (float) Math.log10((double) articleCount
				/ wordsInArticles.get(word));
	}
	
	private int computeMaxTF(Map<String, Integer> wordCounts){
		int maxTF=0;
		for (Entry<String, Integer> wc : wordCounts.entrySet()){
			if(wc.getValue().intValue()>maxTF){
				maxTF = wc.getValue().intValue();
			}
		}
		return maxTF;
	}
	
}
