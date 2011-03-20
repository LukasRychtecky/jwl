package com.jwl.business;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.exceptions.KnowledgeException;
import com.jwl.presentation.global.WikiURLParser;

public class KeyWordPaginator implements IPaginator {

	private int pageSize =5;
	private int pageIndex =1;
	private List<ArticleTO> searchResults;
	private WikiURLParser wup;
	private IKnowledgeManagementFacade knowledge;	
	
	public KeyWordPaginator(WikiURLParser wup,
			IKnowledgeManagementFacade knowledge) {
		super();
		this.wup = wup;
		this.knowledge = knowledge;
	}

	@Override
	public boolean hasNext() {
		if (pageIndex * pageSize < searchResults.size()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPrevious() {
		if (pageIndex != 1) {
			return true;
		}
		return false;
	}

	@Override
	public int getLastPageIndex() {
		if (searchResults.size()% pageSize > 0) {
			return searchResults.size() / this.pageSize + 1;
		}
		return searchResults.size() / this.pageSize;
	}

	@Override
	public int getFirstPageIndex() {
		return 1;
	}

	@Override
	public List<ArticleTO> getcurrentPageArticles() {
		List<ArticleTO> result = new ArrayList<ArticleTO>();
		int beginIndx = (pageIndex-1)*pageIndex;
		for(int i = beginIndx;i<pageSize&&i<searchResults.size();i++){
			result.add(searchResults.get(i));
		}
		return result;
	}

	@Override
	public void setUpPaginator() {
		wup = new WikiURLParser();
		setPageNumber();
	}

	@Override
	public int getPageIndex() {
		return this.pageIndex;
	}

	@Override
	public int getPreviousPageIndex() {
		return this.pageIndex - 1;
	}

	@Override
	public int getNextPageIndex() {
		return this.pageIndex + 1;
	}
	
	private void setPageNumber() {
		String pn = wup.getListPageNumber();
		if (pn == null) {
			return;
		}
		try {
			this.pageIndex = Integer.parseInt(pn);
		} catch (Exception e) {
			this.pageIndex = 1;
		}
	}
	
	public void setSearch(SearchTO searchData){
		try {
			searchResults = knowledge.getKeyWordSearchResult(searchData);
		} catch (KnowledgeException e) {
			searchResults = new ArrayList<ArticleTO>();
		}
	}

}
