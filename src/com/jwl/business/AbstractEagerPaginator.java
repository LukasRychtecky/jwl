package com.jwl.business;

import java.util.ArrayList;
import java.util.List;

import com.jwl.presentation.url.WikiURLParser;

public class AbstractEagerPaginator<T> implements IPaginator<T> {

	protected int pageSize = 3;
	private int pageIndex = 1;
	protected List<T> searchResults;
	protected WikiURLParser wup;

	public AbstractEagerPaginator() {
		super();
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
	public List<T> getCurrentPageContent() {
		setUpPaginator();
		List<T> result = new ArrayList<T>();
		int beginIndx = (pageIndex-1)*pageSize;
		for(int i = beginIndx;i<beginIndx+pageSize&&i<searchResults.size();i++){
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

	@Override
	public int getCurrentPageFirst() {
		return (pageIndex-1)*pageSize+1;
	}

	@Override
	public int getCurrentPageLast() {
		int indx= getCurrentPageFirst()+pageSize-1;
		if(indx>searchResults.size()){
			return searchResults.size();
		}
		return indx;
	}

	@Override
	public int getContentSize() {
		return searchResults.size();
	}

}