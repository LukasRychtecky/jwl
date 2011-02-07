package com.jwl.util.markdown;

import java.util.Map;

import com.jwl.util.html.url.URLBuilder;

public abstract class MarkdownExtension {

	private String currentUrl;
	private Map<String, String> requestParams;
	private String outputParameter; 
	
	public MarkdownExtension(String currentUrl,
			Map<String, String> requestParams, String outputParameter) {
		this.currentUrl = currentUrl;
		this.requestParams = requestParams;
		this.outputParameter = outputParameter;
	}
	
	protected String getInternalWikiLink(String title) {
		requestParams.put(outputParameter, title);
		return URLBuilder.buildURL(currentUrl, requestParams);
	}
	
	public abstract String parse(String markdownSource);
}
