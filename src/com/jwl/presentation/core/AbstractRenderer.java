package com.jwl.presentation.core;

import com.jwl.business.security.IIdentity;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import com.jwl.presentation.url.Linker;
import java.util.ArrayList;
import java.util.Map;


/**
 *
 * @author Lukas Rychtecky
 */
public class AbstractRenderer {

	protected FacesContext context;
	protected Linker linker;
	protected List<UIComponent> components;
	protected Map<String, Object> params;
	protected IIdentity identity;

	public AbstractRenderer(Linker linker, IIdentity identity, Map<String, Object> params) {
		this.context = FacesContext.getCurrentInstance();
		this.linker = linker;
		this.params = params;
		this.identity = identity;
		this.components = new ArrayList<UIComponent>();
	}

	public void renderDefault() throws IOException {
		
	}

	public void render404(String state) throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("State not found: " + state);
		this.components.add(message);
	}

	public void render500() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Service is unavailable, sorry. Please try again later.");
		this.components.add(message);
	}

}
