package com.jwl.presentation.search.enumerations;

public enum SearchCategories {
	TITLE("Title"),
	KEY_WORDS("KeyWords"),
	TAGS("Tags"),
	EDITORS("Editors");
	
	public String where;
	SearchCategories (String where){
		this.where = where;
	}
}
