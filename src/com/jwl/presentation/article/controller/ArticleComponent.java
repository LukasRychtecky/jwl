package com.jwl.presentation.article.controller;

import com.jwl.presentation.component.controller.JWLComponent;
import com.jwl.presentation.component.enumerations.JWLTagAttributes;

public class ArticleComponent extends JWLComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Article";
	public static final String DEFAULT_RENDERER = "com.jwl.component.ArticleRenderer";

	private String initialPage;

	public ArticleComponent() {
		super();
		this.setRendererType(DEFAULT_RENDERER);
	}

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
	
}
