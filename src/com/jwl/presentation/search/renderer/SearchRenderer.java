package com.jwl.presentation.search.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.renderer.JWLCoreRenderer;
import com.jwl.presentation.component.renderer.JWLRenderer;
import com.jwl.presentation.search.controller.SearchController;

public class SearchRenderer extends Renderer {

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
			JWLController controller = new SearchController();
			renderer = new JWLCoreRenderer(controller);
		}
		return renderer;
	}
}
