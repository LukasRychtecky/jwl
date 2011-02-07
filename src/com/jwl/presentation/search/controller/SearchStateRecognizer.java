package com.jwl.presentation.search.controller;

import com.jwl.business.exceptions.ModelException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.presentation.search.enumerations.SearchActions;
import com.jwl.presentation.search.enumerations.SearchStates;

public class SearchStateRecognizer {

	private WikiURLParser wikiURLParser;
	private IFacade facade;
	private SearchActions action;

	public SearchStateRecognizer(IFacade facade){
		this.facade = facade;
		this.wikiURLParser = new WikiURLParser();
	}
	
	public SearchStates getComponentState(UIComponent component) {
		SearchStates state = SearchStates.INIT;
		this.action = getAction();

		switch(action){
		case EDIT:
			state = SearchStates.EDIT;
			break;
		case VIEW:
			if(null != getArticleId()) {
				state = SearchStates.VIEW;
			} else {
				state = SearchStates.INIT;
			}
			break;
		case UNKNOWN:
			state = SearchStates.INIT;
			break;
		default:
			break;
		}
		
		state = this.filterNotAllowedStaes(state);
		return state;
	}
	
	public ArticleId getArticleId() {
		String articleStringId = wikiURLParser.getArticleId();
		if (null != articleStringId){
			try{
				return new ArticleId(Integer.parseInt(articleStringId));
			} catch (NumberFormatException ex) {
				Logger.getLogger(SearchStateRecognizer.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
		ArticleTO article = null;
		try {
			article = this.facade.findArticleByTitle(this.getArticleTitle());
		} catch (ModelException ex) {
			//TODO: process exception
			Logger.getLogger(SearchStateRecognizer.class.getName())
						.log(Level.SEVERE, null, ex);
		}
		ArticleId id = null;
		if (article != null) {
			id = article.getId();
		}
		return id;
	}
	
	private String getArticleTitle() {
		return wikiURLParser.getArticleTitle();
	}

	public SearchActions getAction() {
		String urlAction = wikiURLParser.getAction();

		if (urlAction == null || urlAction.length() == 0) {
			return SearchActions.UNKNOWN;
		} else if (urlAction.equalsIgnoreCase(SearchActions.EDIT.action)) {
			return SearchActions.EDIT;
		} else if (urlAction.equalsIgnoreCase(SearchActions.VIEW.action)) {
			return SearchActions.VIEW;
		} else {
			return SearchActions.UNKNOWN;
		}
	}

	private SearchStates filterNotAllowedStaes(SearchStates state) {
		// TODO: check user permissions
		return state;
//		if (!ArticlePermissions.isRestrictedState(state)) {
//			return state;
//		}
//		IIdentity identity = Global.getInstance().getFacade().getIdentity();
//		String action = ArticlePermissions.mapStateToPermission(state);
//		boolean hasPermission = false;
//		try {
//			hasPermission = identity.hasPermission(action, this.id.getId());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		if (hasPermission) {
//			return state;
//		}
//		return ArticleStates.LIST;
	}

	
}
