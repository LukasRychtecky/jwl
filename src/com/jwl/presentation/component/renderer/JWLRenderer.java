package com.jwl.presentation.component.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface JWLRenderer {

	void decode(FacesContext context, UIComponent component);

	void encodeEnd(FacesContext context, UIComponent component) 
	throws IOException;
	
}
