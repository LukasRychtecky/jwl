package com.jwl.presentation.administration.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.jwl.presentation.administration.controller.AdministrationController;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.renderer.JWLCoreRenderer;
import com.jwl.presentation.component.renderer.JWLRenderer;

/**
 *
 * @author Lukas Rychtecky
 */
public class AdministrationRenderer extends Renderer {

	private JWLRenderer renderer;
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
		getRenderer().decode(context, component);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) 
		throws IOException {
		getRenderer().encodeEnd(context, component);
	}
	
	private JWLRenderer getRenderer() {
		if (renderer == null) {
			JWLController controller = new AdministrationController();
			renderer = new JWLCoreRenderer(controller);
		}
		return renderer;
	}
	
}
