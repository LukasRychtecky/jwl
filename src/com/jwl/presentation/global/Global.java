package com.jwl.presentation.global;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jwl.business.Facade;
import com.jwl.business.IFacade;

/**
 * This class provides access to facade.
 * 
 * @author Lukas Rychtecky
 */
public class Global {

	private static Global instance = null;
	private static String FACADE_VAR = "jwlfacade";
	private IFacade facade;

	/**
	 * Singleton
	 */
	private Global() {

	}

	/**
	 * Returns instance
	 * 
	 * @return
	 */
	public static Global getInstance() {
		if (Global.instance == null) {
			Global.instance = new Global();
		}
		return Global.instance;
	}

	/**
	 * Returns facade
	 * 
	 * @return
	 */
	public IFacade getFacade() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (facade == null) {
			this.facade = (IFacade) session.getAttribute(FACADE_VAR);
		}
		if (facade == null) {
			this.facade = new Facade();
		}
		return facade;
	}

	public IFacade getFacadeOtsideJSF() {
		return new Facade();
	}

	public void saveFacade() {
		if (facade == null) {
			return;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		session.setAttribute(FACADE_VAR, this.facade);
		this.facade = null;
	}

}
