package com.jwl.presentation.article.controller;

import javax.faces.context.FacesContext;

import com.jwl.presentation.component.controller.UIComponentHelper;
import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;

public class ArticleComponent extends AbstractComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Article";

	@Override
	public AbstractPresenter getPresenter(FacesContext context) {
		UIComponentHelper.setUserNameAndRoles(this);
		return new ArticlePresenter(context);
	}

	/*
	private String initialPage;

	public String getInitialPage() {
		if (null == initialPage) {
			Object attribute = this
					.getAttribute(JWLTagAttributes.ARTICLE_INITIAL_PAGE);
			initialPage = this.getNotNullString(attribute);
		}
		return initialPage;
	}

	public void setInitialPage(String initialPage) {
		this.initialPage = initialPage;
	}
	 */
	
}
