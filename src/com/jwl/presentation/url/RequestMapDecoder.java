package com.jwl.presentation.url;

import java.util.Map;

import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.enumerations.JWLElements;

public class RequestMapDecoder {

	protected Map<String, String> reuqestParameterMap;
	private JWLElements rootElement;
	
	public RequestMapDecoder(Map<String, String> reuqestParameterMap) {
		this.reuqestParameterMap = reuqestParameterMap;
	}
	
	public RequestMapDecoder(Map<String, String> reuqestParameterMap, 
			JWLElements rootElement) {
		this(reuqestParameterMap);
		this.rootElement = rootElement;
	}
	
	public boolean containsKey(JWLElements key) {
		return reuqestParameterMap.containsKey(this.getFullKey(key.id, rootElement.id));
	}
	
	public String getValue(JWLElements key) {
		return reuqestParameterMap.get(this.getFullKey(key.id, rootElement.id));
	}
	
	private String getFullKey(String elementId, String formId) {
		if (null != formId && !formId.isEmpty()){
			return formId + AbstractComponent.JWL_HTML_ID_SEPARATOR + elementId; 
		} else {
			return elementId;
		}
	}


	
	
}
