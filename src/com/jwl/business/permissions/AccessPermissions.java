package com.jwl.business.permissions;

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
	ARTICLE_EXCLUDE_ROLE ("Article", "restoreRole"),

	ATTACHMENT_VIEW ("Attachment", "view"),
	ATTACHMENT_ADD ("Attachment", "add"),
	ATTACHMENT_DELETE ("Attachment", "delete");

	private String context;
	private String method;

	private AccessPermissions(String context, String method) {
		this.context = context;
		this.method = method;
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
