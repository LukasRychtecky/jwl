package com.jwl.presentation.search.controller;

import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.business.IFacade;
import com.jwl.business.article.SearchTO;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.controller.RequestParameterMapDecoder;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.global.Global;

public class SearchDecoder implements JWLDecoder {

	private IFacade facade;
	private Map<String, String> parameterMap;
	protected UIComponent component;
	private RequestParameterMapDecoder decoder;
	
	public SearchDecoder(Map<String, String> parameterMap, UIComponent component) {
		this.facade = Global.getInstance().getFacade();
		this.parameterMap = parameterMap;
		this.component = component;
		decoder = new RequestParameterMapDecoder(parameterMap, JWLElements.SEARCH_FORM);
	}

	@Override
	public void processDecode() {
		if (isArticleComponentRequest()) {
			SearchTO searchTO = getFilledSearch();
			this.facade.setSearchParametres(searchTO);
		}
	}

	private SearchTO getFilledSearch() {
		SearchTO searchTO = new SearchTO();
		searchTO.setSearchPhrase(decoder.getMapValue(JWLElements.SEARCH_INPUT));
		this.fillSearchCategory(searchTO);
		return searchTO;
	}

	private boolean isArticleComponentRequest() {
		return parameterMap.containsKey(decoder.getFullKey(JWLElements.SEARCH_BUTTON.id));
	}

	private void fillSearchCategory(SearchTO searchTO) {
		String checkboxValue;

		checkboxValue = decoder.getMapValue(JWLElements.SEARCH_WHERE_TITLE);
		if (checkboxValue != null) {
			searchTO.setTitle(true);
		}

		checkboxValue = decoder.getMapValue(JWLElements.SEARCH_WHERE_KEY_WORDS);
		if (checkboxValue != null) {
			searchTO.setKeyWords(true);
		}

		checkboxValue = decoder.getMapValue(JWLElements.SEARCH_WHERE_EDITORS);
		if (checkboxValue != null) {
			searchTO.setEditors(true);
		}

		checkboxValue = decoder.getMapValue(JWLElements.SEARCH_WHERE_TAGS);
		if (checkboxValue != null) {
			searchTO.setTags(true);
		}

	}

}
