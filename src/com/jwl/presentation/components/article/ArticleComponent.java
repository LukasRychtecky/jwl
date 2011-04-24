package com.jwl.presentation.components.article;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;

public class ArticleComponent extends AbstractComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Article";

	@Override
	public AbstractPresenter getPresenter() {
		return new ArticlePresenter();
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
