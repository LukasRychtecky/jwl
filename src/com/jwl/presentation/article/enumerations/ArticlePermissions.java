package com.jwl.presentation.article.enumerations;

public class ArticlePermissions {
	public static final String ARTICLE_EDIT = "ArticleEdit";
	public static final String ARTICLE_VIEW = "ArticleView";
	public static final String ATTACHMENT_VIEW = "AttachmentView";
	public static final String ATTACHMENT_ADD = "AttachmentAdd";

	public static String mapStateToPermission(ArticleStates state) {
		switch (state) {
		case EDIT:
			return ARTICLE_EDIT;
		case ATTACH_FILE:
			return ATTACHMENT_ADD;
		default:
			return ARTICLE_VIEW;
		}
	}

	public static boolean isRestrictedState(ArticleStates state) {
		if (state == ArticleStates.EDIT || state == ArticleStates.ATTACH_FILE
				|| state == ArticleStates.VIEW) {
			return true;
		}
		return false;
	}
}
