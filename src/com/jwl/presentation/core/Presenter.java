package com.jwl.presentation.core;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Presenter {

	protected FacesContext context;

	public Presenter(FacesContext context) {
		this.context = context;
	}

	protected boolean isAjax() {
		return false;
	}

	protected void redirect(String action) {
	}

	protected Object getRequestParam(String key) {
		return this.context.getExternalContext().getRequestMap().get(key);
	}

	public void logException(Exception e) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}

	public void renderDefault() {
	}

	public void render404() {
		Renderer renderer = new Renderer(this.context);
		try {
			renderer.render404(this.getRequestParam(JWLURLParameters.ACTION).toString());
		} catch (IOException ex) {
			this.logException(ex);
		}
	}

	public void render500() {
		Renderer renderer = new Renderer(this.context);
		try {
			renderer.render500();
		} catch (IOException ex) {
			this.logException(ex);
		}
	}
}
