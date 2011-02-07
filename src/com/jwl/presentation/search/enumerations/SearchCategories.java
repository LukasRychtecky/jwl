package com.jwl.presentation.search.enumerations;

public enum SearchCategories {
	EVERYWHERE("Everywhere"),
	TITLE("Title"),
	TEXT("Text"),
	TAGS("Tags"),
	EDITORS("Editors");
	
	public String where;
	SearchCategories (String where){
		this.where = where;
	}
}
