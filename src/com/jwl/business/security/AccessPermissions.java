package com.jwl.business.security;

/**
 *
 * @author Lukas Rychtecky
 */
public enum AccessPermissions {

	ARTICLE_VIEW ("Article", "view"),
	ARTICLE_EDIT ("Article", "edit"),
	ARTICLE_RENAME ("Article", "rename"),
	ARTICLE_RESTORE ("Article", "restore"),
	ARTICLE_LOCK ("Article", "lock"),
	ARTICLE_DELETE ("Article", "delete"),
	ARTICLE_EXCLUDE_ROLE ("Article", "exclude_role"),
	ARTICLE_RATE ("Article", "rate"),
	KNOWLEDGE_ADMINISTER ("Knowledge", "administer"),

	ATTACHMENT_VIEW ("Attachment", "view"),
	ATTACHMENT_ADD ("Attachment", "add"),
	ATTACHMENT_DELETE ("Attachment", "delete"),
	SECURITY_IMPORT("Security", "import"),
	
	FORUM_VIEW ("Forum", "view"),
	FORUM_CLOSE_TOPIC ("Forum", "close_topic"),
	FORUM_ADD_POST ("Forum", "add_post"),
	FORUM_CREATE_TOPIC ("Forum", "create_topic"),
	FORUM_DELETE_TOPIC ("Forum", "delete_topic"),
	FORUM_DELETE_POST ("Forum", "delete_post");
	

	private String context;
	private String method;

	private AccessPermissions(String context, String method) {
		this.context = context;
		this.method = method;
	}

	public String getContext() {
		return context;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return this.context + "::" + this.method;
	}

	public boolean equals(String context, String method) {
		if (context == null || !context.equals(this.context)) {
			return false;
		}
		if (method == null || !method.equals(this.method)) {
			return false;
		}
		return true;
	}

}
