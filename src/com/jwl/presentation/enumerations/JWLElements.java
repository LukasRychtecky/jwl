package com.jwl.presentation.enumerations;

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
	EDIT_TAGS           	("tags", "Tags"),
	EDIT_CHANGE_NOTE		("changeNote", "Change note"),
	EDIT_SAVE				("edit", "Save"),
	EDIT_SIMILAR_ARTICLE_DIV ("jwl-sad", ""),
	VIEW_SIMILAR_ARTICLE_DIV ("jwl-vsad", ""),
	VIEW_RATING_ARTICLE_ID 	("jwl-rating-article-id", ""),
	VIEW_RATING_RADIO 		("jwl-rating-", ""),
	VIEW_RATING_DIV			("jwl-rating", ""),
	CREATE_SAVE				("create", "Create"),
	SEARCH_FORM				("jwl-search", ""),
	SEARCH_INPUT			("search-text", ""),
	SEARCH_WHERE			("search-where", ""),
	SEARCH_WHERE_TAGS		("search-where-tags", ""),
	SEARCH_WHERE_EDITORS	("search-where-editors", ""),
	SEARCH_WHERE_KEY_WORDS	("search-where-keywords", ""),
	SEARCH_WHERE_TITLE		("search-where-title", ""),
	SEARCH_BUTTON			("search", "Search"),
	KNOWLEDGE_MERGE_SUG_FORM("kn-merge-sug-form",""),
	KNOWLEDGE_ID_PAIR_CHECKBOX("articlePair",""),
	KNOWLEDGE_DEAD_SUG_FORM  ("kn-dead-sug-form", ""),
	KNOWLEDGE_ID_CHECKBOX   ("articleId",""),
	KNOWLEDGE_DEAD_DELETE	("delete","Delete"),
	KNOWLEDGE_INCREASE_LIVABILITY("increase-livability","increase"),
	KNOWLEDGE_LIVABILITY_INPUT("livability-input",""),
	FORUM_CREATE_TOPIC_FORM ("jwl-create-topic", ""),
	FORUM_SUBJECT 			("subject", "Subject:"),
	FORUM_TOPIC_TEXT 		("text", "Text:"),
	FORUM_TOPIC_CREATE 		("create", "Create"),
	FORUM_TOPIC_CANCEL 		("cancel", "Cancel"),
	FORUM_TOPIC_ADMIN_FORM 	("jwl-admin-topic", ""),
	FORUM_TOPIC_CHBX 		("checkbox", ""),
	FORUM_TOPIC_DELETE 		("delete", "delete"),
	FORUM_TOPIC_CLOSE 		("close", "close"),
	FORUM_TOPIC_OPEN 		("open", "open"),
	FORUM_POST_REPLY_FORM 	("jwl-forum-reply", ""),
	FORUM_POST_REPLY 		("reply", "Reply"),
	FORUM_POST_CANCEL 		("cancel", "Cancel"),
	FORUM_POST_TEXT 		("text", ""),
	FORUM_POST_TOPIC_ID		("topic-id",""),
	FORUM_POST_ADMIN_FORM 	("jwl-admin-post", ""),
	
	FORM_DELETE				("delete" , "Delete");
	
	public String id;
	public String value;

	private JWLElements(String id, String value) {
		this.id = id;
		this.value = value;
	}

}
