package com.jwl.presentation.component.controller;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;
import com.jwl.business.Facade;
import com.jwl.business.IFacade;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.IIdentity;
import com.jwl.presentation.component.renderer.FlashMessage;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.presentation.global.Global;

/**
 * 
 * @author Lukas Rychtecky
 */
abstract public class JWLController {

	protected FlashMessage message = null;
	protected IFacade facade;
	protected JWLDecoder decoder;

	public JWLController() {
		this.facade = new Facade();
	}

	protected void assertValidInput(FacesContext context, UIComponent component) {
		if (context == null) {
			throw new NullPointerException("context should not be null");
		} else if (component == null) {
			throw new NullPointerException("component should not be null");
		}
	}

	abstract public void processDecode(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException;

	abstract public JWLEncoder getResponseEncoder(UIComponent component);

	abstract public void processRequest(FacesContext context,
			UIComponent component) throws ModelException, NoPermissionException;
	
	protected void setUserRoles(UIComponent component)
			throws NoPermissionException {
		JWLComponent articleComponent = (JWLComponent) component;
		IIdentity identity = Global.getInstance().getFacade().getIdentity();
		if (!identity.isAuthenticated()) {
			identity.addUserRoles(articleComponent.getRoles());
			identity.authenticate();
		}
	}
	
	protected Map<String, String> getMap(FacesContext context){ 
		return context.getExternalContext().getRequestParameterMap();
	}
	
	/**
	 * Find if user name is set. If yes, return true, else return false.
	 * 
	 * @param component child of JWLComponent which provides getUserName method.
	 * @return true - user name is set, false - is not set
	 */
	protected boolean isSetUserName(UIComponent component) {
		JWLComponent jwlComponent = (JWLComponent) component;
		String userName = jwlComponent.getUserName();
		return !userName.isEmpty();
	}

}
