package com.jwl.presentation.components.search;

import com.jwl.business.article.SearchTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.renderers.EncodeSearch;
import com.jwl.presentation.url.RequestMapDecoder;

public class SearchPresenter extends AbstractPresenter {

	@Override
	public void renderDefault() {
		renderParams.put("paginator", this.getFacade().getSearchPaginator());
		container.addAll(new EncodeSearch(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}

	public void renderSearch() {
		renderParams.put("paginator", this.getFacade().getSearchPaginator());
		container.addAll(new EncodeSearch(linker, getFacade().getIdentity(), renderParams).getEncodedComponent());
	}
	
	public void decodeSearchAction() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.SEARCH_FORM);

		SearchTO searchTO = new SearchTO();
		searchTO.setSearchPhrase(decoder.getValue(JWLElements.SEARCH_INPUT));

		String checkboxValue;

		checkboxValue = decoder.getValue(JWLElements.SEARCH_WHERE_TITLE);
		if (checkboxValue != null) {
			searchTO.setTitle(true);
		}

		checkboxValue = decoder.getValue(JWLElements.SEARCH_WHERE_KEY_WORDS);
		if (checkboxValue != null) {
			searchTO.setKeyWords(true);
		}

		checkboxValue = decoder.getValue(JWLElements.SEARCH_WHERE_EDITORS);
		if (checkboxValue != null) {
			searchTO.setEditors(true);
		}

		checkboxValue = decoder.getValue(JWLElements.SEARCH_WHERE_TAGS);
		if (checkboxValue != null) {
			searchTO.setTags(true);
		}
		
		this.getFacade().setSearchParametres(searchTO);
	}
	
}
