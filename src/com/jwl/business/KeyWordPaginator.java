package com.jwl.business;

import java.util.ArrayList;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.presentation.url.WikiURLParser;

public class KeyWordPaginator extends AbstractEagerPaginator<ArticleTO> {

	private IKnowledgeManagementFacade knowledge;	
	
	public KeyWordPaginator(IKnowledgeManagementFacade knowledge, int pageSize) {
		super();
		this.wup = new WikiURLParser();
		this.knowledge = knowledge;
		this.pageSize = pageSize;
	}

	public void setSearch(SearchTO searchData){
		try {
			searchResults = knowledge.getKeyWordSearchResult(searchData);
		} catch (KnowledgeException e) {
			searchResults = new ArrayList<ArticleTO>();
		}
	}

}
