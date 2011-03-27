package com.jwl.presentation.administration.enumerations;

/**
 *
 * @author lukas
 */
public enum AdministrationActions {

	DELETE ("delete"),
	UNKNOWN ("unknow"),
	LOCK ("lock"),
	UNLOCK ("unlock"),
	EDIT("edit"),
	VIEW("view"),
	HISTORY_VIEW ("history_view"),
	HISTORY_LIST ("history_list"),
	RESTORE ("restore"),
IMPORT_ACL ("import_acl"),
MERGE_SUGGESTION_LIST("merge_suggestion_list"),
	MERGE_SUGGESTION_VIEW("merge_suggestion_view"),
	DEAD_ARTICLE_LIST ("dead_article_list"),
	DEAD_ARTICLE_VIEW ("dead_article_view"),
	FORUM_TOPIC_LIST ("topic_list"),
	FORUM_TOPIC_CREATE ("topic_create");

	public String action;

	private AdministrationActions(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return this.action;
	}

}
