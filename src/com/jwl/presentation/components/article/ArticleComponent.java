package com.jwl.presentation.components.article;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;

public class ArticleComponent extends AbstractComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Article";
	public static final String TAG_PARAM_OPTIONAL_ARTICLE = "initialPage";
	
	@Override
	public AbstractPresenter getPresenter() {
		if (!getInitialPage().isEmpty()) {
			return new ArticlePresenter(getInitialPage());
		} else {
			return new ArticlePresenter();
		}
	}
	
	private String initialPage;

	public String getInitialPage() {
		if (initialPage == null|| initialPage.isEmpty()) {
			Object attribute = this.getAttribute(TAG_PARAM_OPTIONAL_ARTICLE);
			initialPage = this.getNotNullString(attribute);
		}
		return initialPage;
	}

	public void setInitialPage(String initialPage) {
		this.initialPage = initialPage;
	}
	
	
}
