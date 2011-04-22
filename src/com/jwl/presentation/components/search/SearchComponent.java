package com.jwl.presentation.components.search;

import com.jwl.presentation.components.core.AbstractComponent;
import com.jwl.presentation.components.core.AbstractPresenter;

public class SearchComponent extends AbstractComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Search";

	@Override
	public AbstractPresenter getPresenter() {
		return new SearchPresenter();
	}
	
}
