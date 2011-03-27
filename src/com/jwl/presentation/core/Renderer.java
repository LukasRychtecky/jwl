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
	protected Linker linker;

	public Renderer(FacesContext context, Linker linker) {
		this.context = context;
		this.linker = linker;
	}

	public void renderDefault() throws IOException {
		
	}

	public void render404(String action) throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Action not found: " + action);
		message.encodeAll(this.context);
	}

	public void render500() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Service is unavailable, sorry. Please try again later.");
		message.encodeAll(this.context);
	}

}
