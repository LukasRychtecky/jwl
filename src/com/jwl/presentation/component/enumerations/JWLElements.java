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
	SEARCH_WHERE_TAGS		("search-where-tags", ""),
	SEARCH_WHERE_EDITORS	("search-where-editors", ""),
	SEARCH_WHERE_KEY_WORDS	("search-where-keywords", ""),
	SEARCH_WHERE_TITLE		("search-where-title", ""),
	SEARCH_BUTTON			("search", "Search"),
	KNOWLEDGE_MERGE_SUG_FORM("kn-merge-sug-form",""),
	KNOWLEDGE_IGNORE		("ignore", "ignore"),
	KNOWLEDGE_ID_PAIR_CHECKBOX("articlePair",""),
	KNOWLEDGE_DEAD_SUG_FORM  ("kn-dead-sug-form", ""),
	KNOWLEDGE_ID_CHECKBOX   ("articleId",""),
	KNOWLEDGE_DEAD_DELETE	("delete","delete"),
	KNOWLEDGE_INCREASE_LIVABILITY("increase-livability","increase"),
	KNOWLEDGE_LIVABILITY_INPUT("livability-input","");
	
	
	public String id;
	public String value;

	private JWLElements(String id, String value) {
		this.id = id;
		this.value = value;
	}

}
