package com.jwl.presentation.component.enumerations;

public enum JWLElements {
	

	JWL_DIV             	("jwl", ""),
	ARTICLE_DIV         	("jwl-article", ""),
	ADMINISTRATION_DIV  	("jwl-administration", ""),
	ADMINISTRATION_KM_DIV 	("jwl-admin-kn-div", ""),
	ADMINISTRATION_KW_LINK  ("jwl-admin-kn-kw",""),
	SEARCH_DIV          	("jwl-search", ""),
	EDIT_FORM           	("jwl-edit", ""),
	EDIT_ID             	("id", ""),
	EDIT_TITLE          	("title", "Title"),
	EDIT_TEXT           	("text", "Text"),
	EDIT_TAGS           	("tags", "Tags"),
	EDIT_CHANGE_NOTE		("changeNote", "Change note"),
	EDIT_SAVE				("edit", "Save"),
	EDIT_SIMILAR_ARTICLE_DIV ("sad", ""),
	CREATE_SAVE				("create", "Create"),
	FILE_FORM				("jwl-upload",""),
	FILE_TITLE				("fileTitle",""),
	FILE_ARTICLE_TITLE		("articleTitle",""),
	FILE_ITEM				("fileItem",""),
	FILE_DESCRIPTION		("description",""),
	FILE_SAVE				("save", "Upload File"),
	SEARCH_FORM				("jwl-search", ""),
	SEARCH_INPUT			("search-text", ""),
	SEARCH_WHERE			("search-where", ""),
	SEARCH_WHERE_EVERYWHERE	("search-where-everywhere", ""),
	SEARCH_WHERE_TAGS		("search-where-tags", ""),
	SEARCH_WHERE_EDITORS	("search-where-editors", ""),
	SEARCH_WHERE_TEXT		("search-where-text", ""),
	SEARCH_WHERE_TITLE		("search-where-title", ""),
	SEARCH_BUTTON			("search", "Search");
	
	public String id;
	public String value;

	private JWLElements(String id, String value) {
		this.id = id;
		this.value = value;
	}

}
