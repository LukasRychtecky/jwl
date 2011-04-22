package com.jwl.presentation.components.widget;

import com.jwl.presentation.components.core.AbstractComponent;
import com.jwl.presentation.components.core.AbstractPresenter;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetComponent extends AbstractComponent {
	
	public static final String COMPONENT_TYPE = "com.jwl.component.Widget";

	@Override
	public AbstractPresenter getPresenter() {
		return new WidgetPresenter();
	}

}
