package com.jwl.business.knowledge.keyword;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class WordProcessor {
	private static String DELIMITERS = " \f\t\n\r()',.:?!\"[]{}<>*/+-$";
	
	public static Map<String, Integer> getWordsCountInString(String str, boolean useStemmer, String stopWordSPath) {
		Map<String, Integer> foundWords = new HashMap<String, Integer>();
		
		StopWordManager stopMan = new StopWordManager(stopWordSPath);
		StringTokenizer parser = new StringTokenizer(str, DELIMITERS);
		
		String word;
		while (parser.hasMoreTokens()) {
			word = parser.nextToken();
			word = word.toLowerCase();
			if(stopMan.isStopWord(word)){
				continue;
			}
			if(useStemmer){
				word = stem(word);
			}
			try {
				Float.parseFloat(word);
				continue;
			} catch (Throwable t) {
			}
			if (foundWords.containsKey(word)) {
				foundWords.put(word, foundWords.get(word) + 1);
			} else {
				foundWords.put(word, 1);
			}
		}
		return foundWords;
	}

	public static int getWordNumber(String str, boolean useStemmer, String stopWordSPath) {
		Set<String> result = new HashSet<String>();
		if (str == null || str == "") {
			return 0;
		}
		
		StopWordManager stopMan = new StopWordManager(stopWordSPath);
		StringTokenizer parser = new StringTokenizer(str, DELIMITERS);
		
		while (parser.hasMoreTokens()) {
			String word = parser.nextToken();
			word = word.toLowerCase();
			if(stopMan.isStopWord(word)){
				continue;
			}
			result.add(word);			
		}
		if(useStemmer){
			result = stem(result);
		}
		return result.size();
	}

	public static Set<String> getWords(String str, boolean useStemmer, String stopWordSPath) {
		Set<String> result = new HashSet<String>();
		if (str == null || str == "") {
			return result;
		}
		
		StopWordManager stopMan = new StopWordManager(stopWordSPath);
		StringTokenizer parser = new StringTokenizer(str, DELIMITERS);
		
		while (parser.hasMoreTokens()) {
			String word = parser.nextToken();
			word = word.toLowerCase();
			if(stopMan.isStopWord(word)){
				continue;
			}
			result.add(word);
		}
		if(useStemmer){
			result = stem(result);
		}
		return result;
	}

	public static float getWordsSimilarityRatio(Set<String> words1,
			Set<String> words2) {
		int denominator;
		int numerator = 0;
		if (words1.size() == 0 || words2.size() == 0) {
			return 0;
		}
		if (words1.size() < words2.size()) {
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
	
	private static Set<String> stem(Set<String> words){		
		Set<String> result = new HashSet<String>();
		for(String word:words){
			result.add(stem(word));
		}
		return result;
	}
	
	private static String stem(String word){
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.add(word.toCharArray(), word.length());
		stemmer.stem();
		return stemmer.toString();
	}

}
