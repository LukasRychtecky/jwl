package com.jwl.presentation.core;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Presenter {

	protected FacesContext context;
	protected Linker linker;

	public Presenter(FacesContext context) {
		this.context = context;

		String className = this.getClass().getSimpleName();
		String presenterName = className.substring(0, className.lastIndexOf("Presenter"));
		this.linker = new Linker(this.context, presenterName);
	}

	protected boolean isAjax() {
		return false;
	}

	protected void redirect(String action) {
	}

	protected Object getRequestParam(String key) {
		return this.context.getExternalContext().getRequestMap().get(key);
	}

	protected String buildLink(String action) {
		return this.linker.build(action);
	}

	public void logException(Exception e) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}

	public void renderDefault() {
	}

	public void render404() {
		Renderer renderer = new Renderer(this.context, linker);
		try {
			renderer.render404(this.getRequestParam(JWLURLParameters.ACTION).toString());
		} catch (IOException ex) {
			this.logException(ex);
		}
	}

	public void render500() {
		Renderer renderer = new Renderer(this.context, linker);
		try {
			renderer.render500();
		} catch (IOException ex) {
			this.logException(ex);
		}
	}
}
