package com.jwl.presentation.core;

import com.jwl.business.Facade;
import com.jwl.business.IFacade;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractPresenter {

	protected FacesContext context;
	protected Linker linker;
	private IFacade facade = null;

	public AbstractPresenter(FacesContext context) {
		this.context = context;

		String className = this.getClass().getSimpleName();
		String presenterName = className.substring(0, className.lastIndexOf("Presenter"));
		this.linker = new Linker(context, presenterName);
	}

	protected IFacade getFacade() {
		if (this.facade == null) {
			this.facade = new Facade();
		}
		return this.facade;
	}

	protected boolean isAjax() {
		return false;
	}

	protected Object getRequestParam(String key) {
		return this.context.getExternalContext().getRequestMap().get(key);
	}

	protected String buildLink(String action) {
		return this.linker.build(action);
	}

	protected String buildFormLink(String action) {
		return this.linker.buildForm(action);
	}

	protected String getFormValue(String key) {
		return this.context.getExternalContext().getRequestParameterMap().get("jwl:" + key);
	}

	public void logException(Exception e) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}

	public void renderDefault() throws IOException {
	}

	public void render404() throws IOException {
		Renderer renderer = new Renderer(this.context, linker);
		renderer.render404(this.getRequestParam(JWLURLParameters.ACTION).toString());
	}

	public void render500() throws IOException {
		Renderer renderer = new Renderer(this.context, linker);
		renderer.render500();
	}
}
