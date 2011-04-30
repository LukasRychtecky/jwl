package com.jwl.business.knowledge.keyword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.jwl.business.knowledge.util.ISettingsSource;

public class WordCountsFileManager {
	private String filePath;
	private static String RECORD_DELIMETR = ",";
	private static String NUMBER_DELIMETR = ":";
	
	public WordCountsFileManager(ISettingsSource settings){
		filePath = settings.getWordCountsFile();
	}
	
	public void saveToFile(Map<String, Integer> wordCounts) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<String, Integer> e : wordCounts.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(RECORD_DELIMETR);
			}
			sb.append(e.getKey());
			sb.append(NUMBER_DELIMETR);
			sb.append(e.getValue().intValue());
		}
		try {
			saveStringToFile(sb.toString());
		} catch (IOException e) {
		}
	}

	public Map<String, Integer> getFromFile() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		String str;
		try {
			str = getStringFromFile();
		} catch (IOException e) {
			return result;
		}
		List<String> records = parseRecords(str);
		result = createWordCounts(records);
		return result;
	}

	private List<String> parseRecords(String str) {
		List<String> records = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(str, RECORD_DELIMETR);
		while (tokenizer.hasMoreTokens()) {
			String record = tokenizer.nextToken();
			records.add(record);
		}
		return records;
	}

	private Map<String, Integer> createWordCounts(List<String> records) {
		Map<String, Integer> wordCounts = new HashMap<String, Integer>();
		for (String record : records) {
			int delIndx = record.indexOf(NUMBER_DELIMETR);
			Integer intValue = null;
			String key = null;
			try {
				key = record.substring(0, delIndx);
				String value = record.substring(delIndx + 1);
				intValue = new Integer(value);
			} catch (Throwable t) {
				continue;
			}
			wordCounts.put(key, intValue);
		}
		return wordCounts;
	}

	private void saveStringToFile(String str) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(str);
		out.close();
	}

	private String getStringFromFile() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		String str = in.readLine();
		in.close();
		return str;
	}
}
