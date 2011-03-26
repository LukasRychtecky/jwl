package com.jwl.business;

import java.util.List;

public interface IPaginator<T>{
	public boolean hasNext();

	public boolean hasPrevious();

	public int getLastPageIndex();

	public int getFirstPageIndex();

	public  List<T> getCurrentPageContent();

	public int getPageIndex();

	public int getPreviousPageIndex();

	public int getNextPageIndex();
	
	public void setUpPaginator();
	
	public int getCurrentPageFirst();
	
	public int getCurrentPageLast();
	
	public int getContentSize();
	
}
