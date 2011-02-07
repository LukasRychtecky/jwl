package com.jwl.presentation.search.controller;

import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.business.IFacade;
import com.jwl.business.article.SearchTO;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.enumerations.JWLElements;

public class SearchDecoder extends JWLDecoder {

	public SearchDecoder(Map<String, String> map, UIComponent search,
			IFacade facade) {
		super(map, search, facade, JWLElements.SEARCH_FORM.id);
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
		searchTO.setSearchText(getMapValue(JWLElements.SEARCH_INPUT));
		this.fillSearchCategory(searchTO);
		return searchTO;
	}

	private boolean isArticleComponentRequest() {
		return map.containsKey(this.getFullKey(JWLElements.SEARCH_BUTTON.id));
	}

	private void fillSearchCategory(SearchTO searchTO) {
		String checkboxValue;

		checkboxValue = getMapValue(JWLElements.SEARCH_WHERE_EVERYWHERE);
		if (checkboxValue != null) {
			searchTO.setEverywhere(true);
		}

		checkboxValue = getMapValue(JWLElements.SEARCH_WHERE_TITLE);
		if (checkboxValue != null) {
			searchTO.setTitle(true);
		}

		checkboxValue = getMapValue(JWLElements.SEARCH_WHERE_TEXT);
		if (checkboxValue != null) {
			searchTO.setText(true);
		}

		checkboxValue = getMapValue(JWLElements.SEARCH_WHERE_EDITORS);
		if (checkboxValue != null) {
			searchTO.setEditors(true);
		}

		checkboxValue = getMapValue(JWLElements.SEARCH_WHERE_TAGS);
		if (checkboxValue != null) {
			searchTO.setTags(true);
		}

	}

}
