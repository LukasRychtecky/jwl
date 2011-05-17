package com.jwl.presentation.markdown;

import java.util.Map;

import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.url.URLBuilder;

public abstract class MarkdownExtension {

	private String currentUrl;
	private Map<String, String> requestParams;
	
	public MarkdownExtension(String currentUrl, Map<String, String> requestParams) {
		this.currentUrl = currentUrl;
		this.requestParams = requestParams;
	}
	
	protected String getInternalWikiLink(String title) {
		// TODO Add host application URL parameters to internal article link
		requestParams.put(JWLURLParams.ARTICLE_TITLE, title);
		requestParams.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		return URLBuilder.buildURL(currentUrl, requestParams);
	}
	
	public abstract String parse(String markdownSource);
}
