package com.jwl.presentation.components.wiki;

import com.jwl.presentation.components.core.AbstractComponent;
import com.jwl.presentation.components.core.AbstractPresenter;

/**
 *
 * @author Lukas Rychtecky
 */
public class WikiComponent extends AbstractComponent {
	
	public static final String COMPONENT_TYPE = "com.jwl.component.Wiki";

	@Override
	public AbstractPresenter getPresenter() {
		return new WikiPresenter();
	}

}
