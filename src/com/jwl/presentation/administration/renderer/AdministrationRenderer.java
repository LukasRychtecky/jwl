package com.jwl.presentation.administration.renderer;

import com.jwl.presentation.administration.controller.AdministrationController;
import com.jwl.presentation.component.renderer.JWLRenderer;

/**
 *
 * @author Lukas Rychtecky
 */
public class AdministrationRenderer extends JWLRenderer {

	public AdministrationRenderer() {
		this.controller = new AdministrationController();
	}

}
