package com.jwl.presentation.component.controller;

import java.util.Map;

import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.renderer.JWLEncoder;

public class RequestParameterMapDecoder {

	protected Map<String, String> reuqestParameterMap;
	private JWLElements jwlElement;
	
	public RequestParameterMapDecoder(Map<String, String> reuqestParameterMap, 
			JWLElements jwlElement) {
		this.reuqestParameterMap = reuqestParameterMap;
		this.jwlElement = jwlElement;
	}
	
	public String getMapValue(JWLElements key) {
		return reuqestParameterMap.get(this.getFullKey(key.id));
	}
	
	public String getFullKey(String elementId) {
		if (jwlElement != null){
			return jwlElement.id + JWLEncoder.HTML_ID_SEPARATOR + elementId; 
		} else {
			return elementId;
		}
	}
	
	public String getFullKey(String elementId, String formId) {
		if (null != formId && !formId.isEmpty()){
			return formId + JWLEncoder.HTML_ID_SEPARATOR + elementId; 
		} else {
			return elementId;
		}
	}
	
	
}
