package com.jwl.presentation.components.search;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;

public class SearchComponent extends AbstractComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Search";

	@Override
	public AbstractPresenter getPresenter() {
		return new SearchPresenter();
	}
	
}
