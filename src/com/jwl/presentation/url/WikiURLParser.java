package com.jwl.presentation.url;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.jwl.presentation.enumerations.JWLURLParams;

public class WikiURLParser extends URLParser {

	private String uri = null;
	
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
		for(String parameter : JWLURLParams.ALL_URL_PARAMETERS){
			if(urlParameters.containsKey(parameter)){
				urlParameters.remove(parameter);
			}
		}
		return urlParameters;
	}

	public Map<String, String> getURLParametersAndArticleTitle()  {
		Map<String, String> urlParameters = getURLParameters();
		for(String parameter : JWLURLParams.ALL_URL_PARAMETERS_BUT_TITLE){
			if(urlParameters.containsKey(parameter)){
				urlParameters.remove(parameter);
			}
		}
		return urlParameters;
	}
	
	public String getArticleTitle() {
		return getURLParameter(JWLURLParams.ARTICLE_TITLE);
	}
	
	public String getHistoryId() {
		return getURLParameter(JWLURLParams.HISTORY_ID);
	}

	public String getState() {
		return getURLParameter(JWLURLParams.STATE);
	}

	public String getDoAction() {
		return getURLParameter(JWLURLParams.DO);
	}
	
	public String getFileName(){
		return getURLParameter(JWLURLParams.FILE_NAME);
	}
	
	public String getRedirectTarget(){
		return getURLParameter(JWLURLParams.REDIRECT_TARGET);
	}

	public String getTopicId(){
		return this.getURLParameter(JWLURLParams.TOPIC_ID);
	}
	
	public String getPresenter(){
		return this.getURLParameter(JWLURLParams.PRESENTER);
	}
	
	public boolean containsAnswering(){
		return (null != this.getURLParameter(JWLURLParams.ANSWERING));
	}
	
	public String getQuopteTopicId(){
		return this.getURLParameter(JWLURLParams.QUOTE_POST_ID);
	}
	
	public String getListPageNumber(){
		return getURLParameter(JWLURLParams.LIST_PAGE_NUMBER);
	}
	public String getListOrderByColumn(){
		return getURLParameter(JWLURLParams.LIST_ORDER_COLUMN);
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

	public void setURI(String uri) {
		this.uri = uri;
	}

	@Override
	public String getCurrentURI() {
		if (this.uri != null) {
			return this.uri;
		}
		return super.getCurrentURI();
	}
}
