package com.jwl.business.permissions;

/**
 *
 * @author Lukas Rychtecky
 */
public enum AccessPermissionsOld {

	ARTICLE_VIEW ("ArticleView"),
	ARTICLE_EDIT ("ArticleEdit"),
	ARTICLE_RENAME ("ArticleRename"),
	ARTICLE_LOCK ("ArticleLock"),
	ARTICLE_DELETE ("ArticleDelete"),
	ARTICLE_EXCLUDE_ROLE ("ArticleExcludeRole"),
	ATTACHMENT_VIEW ("AttachmentView"),
	ATTACHMENT_ADD ("AttachmentAdd"),
	ATTACHMENT_DELETE ("AttachmentDelete"), 
	ARTICLE_RESTORE ("ArticleRestore");

	private String action;

	private AccessPermissionsOld(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

}
