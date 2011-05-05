package com.jwl.presentation.enumerations;

public enum JWLStates {

	UNKNOWN(""),
	
	ARTICLE_CREATE("create"),
	ARTICLE_EDIT("edit"),
	ARTICLE_VIEW("view"),
	ARTICLE_LIST("list"),
	
	ATTACH_FILE("attachFile"),
	DOWNLOAD_FILE("downloadFile"),
	
	SEARCH("search"),
	
	HISTORY_VIEW("historyView"),
	HISTORY_LIST("historyList"),
	
	MERGE_SUGGESTION_LIST("mergeSuggestionList"),
	MERGE_SUGGESTION_VIEW("mergeSuggestionView"),
	
	DEAD_ARTICLE_LIST ("deadArticleList"),
	DEAD_ARTICLE_VIEW ("deadArticleView"),
	
	FORUM_TOPIC_LIST("topicList"),
	FORUM_TOPIC_CREATE("topicCreate"),
	FORUM_TOPIC_VIEW("topicView"),
	
	AJAX_RATING("rating"),
	AJAX_KEY_WORD_GENERATION("keyWordGeneration"),
	AJAX_ARTICLE_SUGGESTIONS("articleSuggestions");
	
	public final String id;

	JWLStates(String id) {
		this.id = id;
	}
	
	
}
