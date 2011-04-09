package com.jwl.presentation.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.url.URLBuilder;

/**
 *
 * @author Lukas Rychtecky
 */
public class Linker {

	public static final String PREFIX = "jwl";
	private WikiURLParser parser;
	private String presenter;
	private Map<String, String> foreingParams = null;

	public Linker(FacesContext context, String presenter) {
		this.parser = new WikiURLParser(context);
		this.presenter = presenter;
	}

	public String buildForm(String action) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParameters.DO, action);
		return this.buildLink(params);
	}

	public String build(String action, Map<String, String> params) {
		if (!action.equals("default")) {
			params.put(JWLURLParameters.PRESENTER, this.presenter);
			params.put(JWLURLParameters.ACTION, action);
		}
		return this.buildLink(params);
	}

	public String build(String action) {
		return this.build(action, new HashMap<String, String>());
	}

	private String buildLink(Map<String, String> params) {
		params.putAll(this.getForeingParams());
		params.put(JWLURLParameters.ARTICLE_TITLE, this.getArticleTitle());
		return URLBuilder.buildURL(this.parser.getCurrentURI(), params);
	}		

	private Map<String, String> getForeingParams() {
		if (this.foreingParams == null) {
			this.foreingParams = new HashMap<String, String>();

			for (Entry<String, String> param  : this.parser.getURLParameters().entrySet()) {
				if (!param.getKey().startsWith(PREFIX)) {
					this.foreingParams.put(param.getKey(), param.getValue());
				}
			}
		}
		return foreingParams;
	}
	
	private String getArticleTitle() {
		return parser.getArticleTitle();
	}


}
