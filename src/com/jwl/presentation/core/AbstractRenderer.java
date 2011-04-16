package com.jwl.presentation.core;

import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;


/**
 *
 * @author Lukas Rychtecky
 */
public class AbstractRenderer {

	protected FacesContext context;
	protected Linker linker;
	protected List<UIComponent> components;

	public AbstractRenderer(FacesContext context, Linker linker, List<UIComponent> components) {
		this.context = context;
		this.linker = linker;
		this.components = components;
	}

	public void renderDefault() throws IOException {
		
	}

	public void render404(String action) throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Action not found: " + action);
		this.components.add(message);
	}

	public void render500() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Service is unavailable, sorry. Please try again later.");
		this.components.add(message);
	}

}
