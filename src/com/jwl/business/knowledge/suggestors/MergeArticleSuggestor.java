package com.jwl.business.knowledge.suggestors;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.KeyWordTO;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.business.knowledge.keyword.WordProcessor;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.business.knowledge.util.ArticleIterator;
import com.jwl.business.knowledge.util.IArticleIterator;
import com.jwl.business.knowledge.util.ISettings;
import com.jwl.business.knowledge.util.Neuron;
import com.jwl.business.knowledge.util.WeightRecord;
import com.jwl.integration.article.IArticleDAO;

public class MergeArticleSuggestor extends AbstractArticleSuggestor {

	private String mergeFile;
	private String mergeIgnoreFile;

	public MergeArticleSuggestor(IArticleDAO articleDAO,
			ISettings settings) {
		super(articleDAO, settings);
		this.neuron = new Neuron(settings, "MergeSuggestion");
		mergeFile = settings.getMergeFile();
		mergeIgnoreFile = settings.getMergeIgnoreFile();
	}

	public List<ArticleIdPair> suggestArticleMerge(){
		Map<ArticleIdPair, Float> articleIdPairs = new HashMap<ArticleIdPair, Float>();
		IArticleIterator iterator1 = new ArticleIterator(articleDAO, 50);
		List<ArticleIdPair> ignoredPairs = null;
		try{
			ignoredPairs = getIdPairsFromFile(mergeIgnoreFile);
		}catch(KnowledgeException e1){
			ignoredPairs = new ArrayList<ArticleIdPair>();
		}
		try{
			while(iterator1.hasNext()){
				ArticleTO article1 = iterator1.getNextArticle();
				Set<String> a1NameWords = WordProcessor.getWords(article1
						.getTitle(), settings.getUsePorterStamer(), settings.getStopWordSetPath());
				Map<String, Float> a1KeyWords = getKeyWordWeifgts(article1);
				
				IArticleIterator iterator2 = new ArticleIterator(articleDAO, 50);
				while(iterator2.hasNext()){
					ArticleTO article2 = iterator2.getNextArticle();
					ArticleIdPair aip = new ArticleIdPair(article1.getId(),
							article2.getId());
					if(article1.getId().equals(article2.getId())
							|| articleIdPairs.containsKey(aip)
							|| ignoredPairs.contains(aip)){
						continue;
					}
					Map<String, WeightRecord> neuronInput = processArticle(
							a1NameWords, article1.getTags(), a1KeyWords,
							article2);
					float neuronOutput = neuron.getOutput(neuronInput);
					if(neuronOutput != 0){
						articleIdPairs.put(new ArticleIdPair(article1.getId(),
								article2.getId()), neuronOutput);
					}
				}
			}
		}catch(Exception e){
			return null;
		}

		return createOrderedPairList(articleIdPairs);
	}

	public void pregenerateMergeSuggestion() throws KnowledgeException{
		List<ArticleIdPair> suggestedMerge = suggestArticleMerge();
		saveIdPairsToFile(suggestedMerge, mergeFile);
	}

	public List<ArticleIdPair> getPregeneratedMergeSuggestions()
			throws KnowledgeException{
		List<ArticleIdPair> mergeSuggestions = getIdPairsFromFile(mergeFile);
		filterIgnored(mergeSuggestions);
		return mergeSuggestions;
	}

	private void filterIgnored(List<ArticleIdPair> mergeSuggestions){
		List<ArticleIdPair> ignoredPairs = null;
		try{
			ignoredPairs = getIdPairsFromFile(mergeIgnoreFile);
		}catch(KnowledgeException e){
			return;
		}
		mergeSuggestions.removeAll(ignoredPairs);
	}

	private List<ArticleIdPair> createOrderedPairList(
			Map<ArticleIdPair, Float> wordWeights){
		List<Entry<ArticleIdPair, Float>> list = new LinkedList<Map.Entry<ArticleIdPair, Float>>(
				wordWeights.entrySet());
		Collections.sort(list, new Comparator<Entry<ArticleIdPair, Float>>() {
			@Override
			public int compare(Entry<ArticleIdPair, Float> arg0,
					Entry<ArticleIdPair, Float> arg1){
				return arg1.getValue().compareTo(arg0.getValue());
			}
		});
		List<ArticleIdPair> result = new ArrayList<ArticleIdPair>();
		for (Entry<ArticleIdPair, Float> e : list){
			result.add(e.getKey());
		}
		return result;
	}

	public void addIgnoredMergeSuggestion(ArticleIdPair articleIdPair)
			throws KnowledgeException{
		File f = new File(mergeIgnoreFile);
		List<ArticleIdPair> ignoredIdPairs = null;
		if(f.exists()){
			ignoredIdPairs = getIdPairsFromFile(mergeIgnoreFile);
		}else{
			ignoredIdPairs = new ArrayList<ArticleIdPair>();
		}
		ignoredIdPairs.add(articleIdPair);
		saveIdPairsToFile(ignoredIdPairs, mergeIgnoreFile);
	}

	public void cleanIgnoredMergeSuggestions(){
		List<ArticleIdPair> ignoredPairs = null;
		try{
			ignoredPairs = getIdPairsFromFile(mergeIgnoreFile);
		}catch(KnowledgeException e){
			return;
		}
		try{
			for (ArticleIdPair idPair : ignoredPairs){
				ArticleTO a1 = articleDAO.get(idPair.getId1());
				ArticleTO a2 = articleDAO.get(idPair.getId2());
				if(a1 == null || a2 == null){
					ignoredPairs.remove(idPair);
				}
			}
			saveIdPairsToFile(ignoredPairs, mergeIgnoreFile);
		}catch(Exception e){
		}
	}

	private void saveIdPairsToFile(List<ArticleIdPair> idPairs, String filePath)
			throws KnowledgeException{
		try{
			File f = new File(filePath);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(idPairs);
			out.close();
		}catch(IOException e){
			throw new KnowledgeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private List<ArticleIdPair> getIdPairsFromFile(String filePath)
			throws KnowledgeException{
		List<ArticleIdPair> result = null;
		try{
			File f = new File(filePath);
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream in = new ObjectInputStream(fis);
			result = (List<ArticleIdPair>) in.readObject();
		}catch(Exception e){
			throw new KnowledgeException(e);
		}
		return result;
	}
	
	private Map<String, Float> getKeyWordWeifgts(ArticleTO article){
		Map<String, Float> result = new HashMap<String, Float>();
		for (KeyWordTO kw : article.getKeyWords()) {
			result.put(kw.getWord(), kw.getWeight().floatValue());
		}
		return result;
	}
}
