package com.jwl.presentation.component.controller;

import java.util.Map;
import javax.faces.component.UIComponent;
import com.jwl.business.IFacade;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.renderer.JWLEncoder;
import javax.naming.NoPermissionException;

public abstract class JWLDecoder {

	protected Map<String, String> map;
	protected UIComponent component;
	protected IFacade facade;
	private String formId;
	
	public JWLDecoder(Map<String, String> map, UIComponent component,
			IFacade facade, String formId) {
		super();
		this.map = map;
		this.component = component;
		this.facade = facade;
		this.formId = formId;
	}
	
	abstract public void processDecode() throws ModelException, NoPermissionException;
	
	protected String getLogedUser() {
		return ((JWLComponent) component).getUserName();
	}
	
	protected String getMapValue(JWLElements key){
		return map.get(this.getFullKey(key.id));
	}
	
	protected String getFullKey(String elementId) {
		if (null != formId || !formId.isEmpty()){
			return formId + JWLEncoder.HTML_ID_SEPARATOR + elementId; 
		} else {
			return elementId;
		}
	}
	
	
}
