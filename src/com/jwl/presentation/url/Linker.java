package com.jwl.presentation.url;

import java.util.HashMap;
import java.util.Map;

import com.jwl.presentation.enumerations.JWLURLParams;

/**
 *
 * @author Lukas Rychtecky
 */
public class Linker {

	private WikiURLParser parser;
	private String presenter;

	public Linker(String presenter, String uri) {
		this(presenter);
		this.parser.setURI(uri);
	}

	public Linker(String presenter) {
		this.parser = new WikiURLParser();
		this.presenter = presenter;
	}
	
	public String buildForm(String doAction) {
		Map<String, String> params = new HashMap<String, String>();

		if (doAction != null) {
			params.put(JWLURLParams.DO, doAction);
		}
		
		return this.buildLink(params);
	}
	
	public String buildLink(String state) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, state);
		return this.buildLink(params);
	}
	
	public String buildLink(Map<String, String> params) {
		return buildLink(this.parser.getCurrentURI(), params);
	}
	
	public String buildLink(String targetUri, Map<String, String> params) {
		Map<String, String> originalParams = this.parser.getURLParametersMinusArticleParameters();
		params.putAll(originalParams);
		
		if (!params.containsKey(JWLURLParams.PRESENTER) && presenter != null) {
			params.put(JWLURLParams.PRESENTER, this.presenter);
		}
		
		return URLBuilder.buildURL(targetUri, params);
	}

}
