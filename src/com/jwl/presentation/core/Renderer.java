package com.jwl.presentation.core;

import java.io.IOException;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;


/**
 *
 * @author Lukas Rychtecky
 */
public class Renderer {

	protected FacesContext context;

	public Renderer(FacesContext context) {
		this.context = context;
	}

	public void renderDefault() throws IOException {
		
	}

	public void render404(String action) throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Action not found: " + action);
		message.encodeAll(this.context);
	}

}
