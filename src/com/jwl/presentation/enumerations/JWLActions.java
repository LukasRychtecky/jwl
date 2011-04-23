package com.jwl.presentation.enumerations;

public enum JWLActions {

	UNKNOWN (""),
	
	ARTICLE_CREATE("articleCreate"),
	ARTICLE_UPDATE("articleUpdate"),
	ARTICLE_DELETE("articleDelete"),
	ARTICLE_LOCK("articleLock"),
	ARTICLE_UNLOCK("articleUnlock"),

	SEARCH_ACTION("searchAction"),
	
	FILE_UPLOAD("fileUpload"),
	IMPORT_ACL("import_acl"),
	
	HISTORY_RESTORE("historyRestore"),
	
	FORUM_TOPIC_CREATE("topicCreate"),
	FORUM_TOPIC_LIST("topicList"),
	
	FORUM_POST_REPLY("postReplyRequest");

	public final String id;
	
	JWLActions(String id) {
		this.id = id;
	}
}
