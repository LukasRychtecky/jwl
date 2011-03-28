package com.jwl.business.knowledge.keyword;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class WordProcessor {
	private static String DELIMITERS = " \f\t\n\r()',.:?!\"";

	public static Map<String, Integer> getWordsCountInString(String str) {
		Map<String, Integer> foundWords = new HashMap<String, Integer>();
		StringTokenizer parser = new StringTokenizer(str, DELIMITERS);
		String word;
		while (parser.hasMoreTokens()) {
			word = parser.nextToken();
			word = word.toLowerCase();
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

	public static int getWordNumber(String str) {
		StringTokenizer parser = new StringTokenizer(str, DELIMITERS);
		int count = 0;
		while (parser.hasMoreTokens()) {
			parser.nextToken();
			count++;
		}
		return count;
	}

	public static Set<String> getWords(String str) {
		Set<String> result = new HashSet<String>();
		if (str == null || str == "") {
			return result;
		}
		StringTokenizer parser = new StringTokenizer(str, DELIMITERS);
		while (parser.hasMoreTokens()) {
			String word = parser.nextToken();
			word = word.toLowerCase();
			result.add(word);
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

}
