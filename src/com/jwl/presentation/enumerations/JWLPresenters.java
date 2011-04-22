package com.jwl.presentation.enumerations;

import com.jwl.presentation.components.administration.AdministrationPresenter;
import com.jwl.presentation.components.article.ArticlePresenter;
import com.jwl.presentation.components.core.AbstractPresenter;
import com.jwl.presentation.components.search.SearchPresenter;

public enum JWLPresenters {

	ARTICLE("article", ArticlePresenter.class),
	ADMINISTRATION("administration", AdministrationPresenter.class),
	SEARCH("search", SearchPresenter.class);
	
	public final String id;
	public final Class<? extends AbstractPresenter> clazz;

	private JWLPresenters(String id, Class<? extends AbstractPresenter> clazz) {
		this.id = id;
		this.clazz = clazz;
	}
	
	public static final JWLPresenters getFromId(String id) {
		for (JWLPresenters value : values()) {
			if (value.id.equalsIgnoreCase(id)) {
				return value;
			}
		}
		return null;
	}
	
}
