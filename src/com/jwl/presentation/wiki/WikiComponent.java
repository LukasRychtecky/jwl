package com.jwl.presentation.wiki;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WikiComponent extends AbstractComponent {
	
	public static final String COMPONENT_TYPE = "com.jwl.component.Wiki";

	@Override
	public AbstractPresenter getPresenter(FacesContext context) {
		return new WikiPresenter(context);
	}

}
