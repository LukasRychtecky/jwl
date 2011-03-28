package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Component extends AbstractComponent {
	
	public static final String COMPONENT_TYPE = "com.jwl.component.Widget";

	@Override
	public AbstractPresenter getPresenter(FacesContext context) {
		return new WidgetPresenter(context);
	}

}
