package com.jwl.business.knowledge.keyword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class StopWordManager {

	private Set<String> stopWords = null;
	private static String RECORD_DELIMETR = ",";

	public StopWordManager(String wordSetPath) {
		getStopWords(wordSetPath);
	}
	
	public boolean isStopWord(String word){
		return stopWords.contains(word);
	}

	private Set<String> getStopWords(String wordSetPath){
		if(stopWords == null){
			stopWords = getFromFile(wordSetPath);
		}
		return stopWords;
	}

	private Set<String> getFromFile(String wordSetPath){

		String str;
		try{
			str = getStringFromFile(wordSetPath);
		}catch(IOException e){
			return new HashSet<String>();
		}
		Set<String> result = parseWords(str);
		return result;
	}

	private Set<String> parseWords(String str){
		Set<String> records = new HashSet<String>();
		StringTokenizer tokenizer = new StringTokenizer(str, RECORD_DELIMETR);
		while(tokenizer.hasMoreTokens()){
			String word = tokenizer.nextToken();
			word = word.trim();
			records.add(word);
		}
		return records;
	}

	private String getStringFromFile(String wordSetPath) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(wordSetPath));
		String str = in.readLine();
		in.close();
		return str;
	}
}
