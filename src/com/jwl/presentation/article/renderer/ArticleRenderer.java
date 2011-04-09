package com.jwl.presentation.article.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.jwl.presentation.article.controller.ArticleController;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.renderer.JWLRenderer;
import com.jwl.presentation.component.renderer.JWLCoreRenderer;

public class ArticleRenderer extends Renderer {

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
			JWLController controller = new ArticleController();
			renderer = new JWLCoreRenderer(controller);
		}
		return renderer;
	}
	
}
