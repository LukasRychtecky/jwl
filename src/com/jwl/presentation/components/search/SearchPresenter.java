package com.jwl.presentation.components.search;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.components.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.renderers.EncodeListing;
import com.jwl.presentation.renderers.EncodeNotExist;
import com.jwl.presentation.renderers.EncodeSearch;
import com.jwl.presentation.url.RequestMapDecoder;

public class SearchPresenter extends AbstractPresenter {

	@Override
	public void renderDefault() {
		String articleTitle = getArticleTitle();
		IPaginator<ArticleTO> paginator = this.getFacade().getPaginator();
		if (articleTitle != null) {
			new EncodeNotExist().getEncodedComponent();
		} else {
			new EncodeListing(paginator).getEncodedComponent();
		}
	}

	public void renderSearch() {
		IPaginator<ArticleTO> paginator = this.getFacade().getSearchPaginator();
		container.addAll(new EncodeSearch(paginator).getEncodedComponent());
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
		
		renderSearch();
	}
	
}
