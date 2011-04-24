package com.jwl.presentation.components.administration;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
/**
 *
 * @author Lukas Rychtecky
 */
public class AdministrationComponent extends AbstractComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Administration";

	@Override
	public AbstractPresenter getPresenter() {
		return new AdministrationPresenter();
	}
	

}
