package com.jwl.business.usecases.interfaces;

import java.util.List;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.util.ArticleIdPair;

public interface IAddToMergeSuggestionsIgnoreUC {
	
	public void addToIgnored(List<ArticleIdPair> idPairs) throws ModelException;
}
