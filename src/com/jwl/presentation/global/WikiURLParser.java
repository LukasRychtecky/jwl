package com.jwl.presentation.global;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.util.html.url.URLParser;

public class WikiURLParser extends URLParser {
	
	public WikiURLParser(){
		setRequest(getHttpRequest());
	}
	
	public WikiURLParser(HttpServletRequest request) {
		setRequest(request);
	}

	public WikiURLParser(FacesContext context) {
		super.setRequest((HttpServletRequest) context.getExternalContext().getRequest());
	}

	public Map<String, String> getURLParametersMinusArticleParameters()  {
		Map<String, String> urlParameters = getURLParameters();
		for(String parameter : JWLURLParameters.ALL_URL_PARAMETERS){
			if(urlParameters.containsKey(parameter)){
				urlParameters.remove(parameter);
			}
		}
		return urlParameters;
	}

	public Map<String, String> getURLParametersAndArticleTitle()  {
		Map<String, String> urlParameters = getURLParameters();
		for(String parameter : JWLURLParameters.ALL_URL_PARAMETERS_BUT_TITLE){
			if(urlParameters.containsKey(parameter)){
				urlParameters.remove(parameter);
			}
		}
		return urlParameters;
	}
	
	public String getArticleTitle() {
		return getURLParameter(JWLURLParameters.ARTICLE_TITLE);
	}

	public String getArticleId() {
		return getURLParameter(JWLURLParameters.ARTICLE_ID);
	}

	public String getHistoryId() {
		return getURLParameter(JWLURLParameters.HISTORY_ID);
	}
	
	public String getAction() {
		return getURLParameter(JWLURLParameters.ACTION);
	}
	
	public String getFileName(){
		return getURLParameter(JWLURLParameters.FILE_NAME);
	}
	
	public String getRedirectTarget(){
		return getURLParameter(JWLURLParameters.REDIRECT_TARGET);
	}

	public String getFileAction() {
		return this.getURLParameter(JWLURLParameters.FILE_ACTION);
	}
	
	public String getListPageNumber(){
		return getURLParameter(JWLURLParameters.LIST_PAGE_NUMBER);
	}
	public String getListOrderByColumn(){
		return getURLParameter(JWLURLParameters.LIST_ORDER_COLUMN);
	}
	
	private HttpServletRequest getHttpRequest() {
		try {
			return (HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}
}
