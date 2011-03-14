package com.jwl.presentation.article.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.IIdentity;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.article.enumerations.ArticlePermissions;
import com.jwl.presentation.article.enumerations.ArticleStates;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.global.WikiURLParser;

public class ArticleStateRecognizer {

	private WikiURLParser wikiURLParser;
	private IFacade facade;
	private ArticleId id;
	private String action;
	private String articleTitle;

	public ArticleStateRecognizer(IFacade facade){
		this.facade = facade;
		this.wikiURLParser = new WikiURLParser();
	}
	
	public ArticleStates getComponentState(UIComponent component) throws ModelException {
		ArticleStates state = ArticleStates.LIST;
		this.action = getAction();
		this.articleTitle = getArticleTitle();
		ArticleTO article = null;
		article = this.facade.findArticleByTitle(this.articleTitle);

		if (article != null) {
			this.id = article.getId();
		}

		if (action.equals(ArticleActions.EDIT)) {
			if (articleTitle == null) {
				state = ArticleStates.CREATE;
			} else if (id == null) {
				state = ArticleStates.CREATE;
			} else {
				state = ArticleStates.EDIT;
			}
		} else if (action.equals(ArticleActions.LIST)) {
			if (articleTitle == null) {
				state = ArticleStates.LIST;
			} else if (id == null) {
				state = ArticleStates.LIST;
			} else {
				state = ArticleStates.VIEW;
			}
		} else if (action.equals(ArticleActions.ATTACH_FILE)) {
			state = ArticleStates.ATTACH_FILE;

		} else if (action.equals(ArticleActions.UNKNOWN)) {
			if (articleTitle == null) {
				if (isInitialPageSet(component)) {
					state = ArticleStates.VIEW;
				} else {
					state = ArticleStates.LIST;
				}
			} else if (id == null) {
				state = ArticleStates.NOT_EXIST;
			} else {
				state = ArticleStates.VIEW;
			}
		} else if (action.equals(ArticleActions.HISTORY_LIST)) {
			 if (this.getArticleId() != null) {
					state = ArticleStates.HISTORY_LIST;
				} else {
					state = ArticleStates.LIST;
				}
		} else if (action.equals(ArticleActions.HISTORY_VIEW)) {
			if (!this.wikiURLParser.getHistoryId().isEmpty()) {
				state = ArticleStates.HISTORY_VIEW;
			} else {
				state = ArticleStates.LIST;
			}
		}

		state = this.filterNotAllowedStates(state);
		state = this.filterLockedStates(state);
		return state;
	}
	
	private String getArticleTitle() {
		return wikiURLParser.getArticleTitle();
	}

	public HistoryId getHistoryId() {
		return new HistoryId(Integer.parseInt(this.wikiURLParser.getHistoryId()), this.getArticleId());
	}

	public String getAction() {
		String actionParameter = wikiURLParser.getAction();

		if (actionParameter == null) {
			return ArticleActions.UNKNOWN;
		} else if (actionParameter.equalsIgnoreCase(ArticleActions.EDIT)) {
			return ArticleActions.EDIT;
		} else if (actionParameter.equalsIgnoreCase(ArticleActions.LIST)) {
			return ArticleActions.LIST;
		} else if (actionParameter.equalsIgnoreCase(ArticleActions.ATTACH_FILE)) {
			return ArticleActions.ATTACH_FILE;
		} else if (actionParameter.equalsIgnoreCase(ArticleActions.HISTORY_LIST)) {
			return ArticleActions.HISTORY_LIST;
		} else if (actionParameter.equalsIgnoreCase(ArticleActions.RESTORE)) {
			return ArticleActions.RESTORE;
		} else if (actionParameter.equalsIgnoreCase(ArticleActions.HISTORY_VIEW)) {
			return ArticleActions.HISTORY_VIEW;
		} else {
			return ArticleActions.UNKNOWN;
		}
	}

	private ArticleStates filterNotAllowedStates(ArticleStates state) {
		if (!ArticlePermissions.isRestrictedState(state)) {
			return state;
		}
		IIdentity identity = Global.getInstance().getFacade().getIdentity();
		try {
			identity.checkPermission(ArticlePermissions.mapStateToPermission(state), id);
			return state;
		} catch (ModelException ex) {
			Logger.getLogger(ArticleStateRecognizer.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ArticleStates.LIST;
	}

	private ArticleStates filterLockedStates(ArticleStates state) throws ModelException {
		if (state != ArticleStates.EDIT && state != ArticleStates.ATTACH_FILE) {
			return state;
		}
		IFacade facade = Global.getInstance().getFacade();
		ArticleTO article = null;
		article = facade.getArticle(this.id);

		if (article.isLocked()) {
			return ArticleStates.VIEW;
		}
		return state;
	}
	
	private boolean isInitialPageSet(UIComponent component) throws ModelException {
		if (!(component instanceof ArticleComponent)) {
			return false;
		}
		
		ArticleComponent articleComponent = (ArticleComponent) component;
		String initialPage = articleComponent.getInitialPage();
		ArticleTO article = null;
		article = this.facade.findArticleByTitle(initialPage);
		if (article != null) {
			this.id = article.getId();
		}
		return null != this.id;
	}

	public ArticleId getArticleId() {
		if (this.id == null && this.wikiURLParser.getArticleId() != null) {
			this.id = new ArticleId(Integer.parseInt(this.wikiURLParser.getArticleId()));
		}
		return this.id;
	}
}
