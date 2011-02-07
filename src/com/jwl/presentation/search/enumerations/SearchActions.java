package com.jwl.presentation.search.enumerations;

public enum SearchActions {

	UNKNOWN(""), 
	EDIT("edit"), 
	VIEW("view");

	public String action;

	private SearchActions(String action) {
		this.action = action;
	}

}
