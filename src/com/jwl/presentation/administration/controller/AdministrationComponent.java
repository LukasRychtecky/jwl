package com.jwl.presentation.administration.controller;

import com.jwl.presentation.component.controller.JWLComponent;
/**
 *
 * @author Lukas Rychtecky
 */
public class AdministrationComponent extends JWLComponent {

	// TODO PD
	
	public static final String COMPONENT_TYPE = "com.jwl.component.Administration";
	public static final String DEFAULT_RENDERER = "com.jwl.component.AdministrationRenderer";

	public AdministrationComponent() {
		super();
		this.setRendererType(DEFAULT_RENDERER);
	}

}
