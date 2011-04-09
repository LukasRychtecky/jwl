package com.jwl.presentation.component.controller;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.jwl.business.IFacade;
import com.jwl.business.security.Role;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.global.WikiURLParser;

public class UIComponentHelper {

	public static void assertValidInput(FacesContext context, UIComponent component) {
		if (context == null) {
			throw new NullPointerException("context should not be null");
		} else if (component == null) {
			throw new NullPointerException("component should not be null");
		}
	}

	public static void setUserNameAndRoles(UIComponent component) {
		JWLComponent jwlComponent = (JWLComponent) component;
		
		String userName = jwlComponent.getUserName();
		List<Role> roles = jwlComponent.getRoles();

		if (userName.isEmpty()) {
			WikiURLParser parser = new WikiURLParser();
			userName = parser.getUserIP();
		}
		
		IFacade facade = Global.getInstance().getFacade();
		facade.getIdentity().addUserName(userName);
		facade.getIdentity().addUserRoles(roles);
	}
	
	public static String getLogedUser(UIComponent component) {
		return ((JWLComponent) component).getUserName();
	}

	/**
	 * Find if user name is set. If yes, return true, else return false.
	 * 
	 * @param component child of JWLComponent which provides getUserName method.
	 * @return true - user name is set, false - is not set
	 */
	public static boolean isSetUserName(UIComponent component) {
		JWLComponent jwlComponent = (JWLComponent) component;
		String userName = jwlComponent.getUserName();
		return !userName.isEmpty();
	}

}
