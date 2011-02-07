package com.jwl.util.html.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides support for wiki part of the URL
 * 
 * @author Petr Janouch
 * 
 */

public class URLParser {

	public static final String CHARSET = "UTF-8";
	
	private Map<String, String> urlParameters;

	private HttpServletRequest httpServletRequest;
	
	public URLParser() {
		urlParameters = Collections.emptyMap();
	}
	
	public String getUserIP() {
		this.assertValidState();
		return httpServletRequest.getRemoteAddr();
	}
	
	public String getCurrentContext() {
		this.assertValidState();
		String requestURI = httpServletRequest.getRequestURI();
		int contextDeliminer = requestURI.lastIndexOf("/");
		if (contextDeliminer == 1){
			return "";
		} else {
			return requestURI.substring(1, contextDeliminer);
		}
	}
	
	public String getCurrentPage() {
		this.assertValidState();
		return httpServletRequest.getServletPath().substring(1);
	}
	
	public String getCurrentURI() {
		this.assertValidState();
		return httpServletRequest.getRequestURI().toString();
	}
	
	public String getCurrentURL() {
		this.assertValidState();
		return httpServletRequest.getRequestURL().toString();
	}
	
	public String getURLParameter(String parameter) {		
		return urlParameters.get(parameter);
	}
	
	public Map<String, String> getURLParameters() {
		return urlParameters;
	}

	public void setRequest(HttpServletRequest httpServletRequest) {
		if(this.httpServletRequest != httpServletRequest) {
			this.httpServletRequest = httpServletRequest;
			this.assertValidState();
			parseRequestParameters();
		}
	}
	
	private void assertValidState() {
		if(this.httpServletRequest == null)
			throw new IllegalStateException("Http request must be set.");
	}
	
	private void parseRequestParameters() {
		urlParameters = new HashMap<String, String>();
		try {
			String query = httpServletRequest.getQueryString();
			if(null == query) {
				return;
			}
			
			if(query.contains("#")) {
				query = query.substring(0, query.indexOf("#"));
			}
			
			String[] parameterArray = query.split("&");
			
			for (String parameterString : parameterArray) {
				String[] attr = parameterString.split("=");
				
				String parameter = attr[0];
				String value = null;
				
				if(attr.length > 1 && attr[1] != null){
					value = attr[1].trim();
				} else {
					value = "";
				}
				
				parameter = URLDecoder.decode(parameter, CHARSET);
				value = URLDecoder.decode(value, CHARSET);
					
				urlParameters.put(parameter, value);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 	
	}

}
