package com.jwl.presentation.article.enumerations;

import com.jwl.business.security.AccessPermissions;

public class ArticlePermissions {

	public static final String ARTICLE_EDIT = "ArticleEdit";
	public static final String ARTICLE_VIEW = "ArticleView";
	public static final String ATTACHMENT_VIEW = "AttachmentView";
	public static final String ATTACHMENT_ADD = "AttachmentAdd";

	public static AccessPermissions mapStateToPermission(ArticleStates state) {
		switch (state) {
			case EDIT:
				return AccessPermissions.ARTICLE_EDIT;
			case ATTACH_FILE:
				return AccessPermissions.ATTACHMENT_ADD;
			default:
				return AccessPermissions.ARTICLE_VIEW;
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
