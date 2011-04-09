package com.jwl.presentation.component.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.renderer.JWLEncoder;

public interface JWLController {

	void processDecode(FacesContext context, UIComponent component)
	throws NoPermissionException, ModelException;

	JWLEncoder getResponseEncoder(UIComponent component);

	void processRequest(FacesContext context, UIComponent component) 
	throws ModelException, NoPermissionException;
	
}
