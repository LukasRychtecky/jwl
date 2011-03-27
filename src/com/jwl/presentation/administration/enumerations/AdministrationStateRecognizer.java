package com.jwl.presentation.administration.enumerations;

import com.jwl.business.exceptions.ModelException;
import javax.faces.component.UIComponent;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.presentation.global.WikiURLParser;

public class AdministrationStateRecognizer {

	private WikiURLParser wikiURLParser;
	private IFacade facade;

	public AdministrationStateRecognizer(IFacade facade) {
		this.facade = facade;
		this.wikiURLParser = new WikiURLParser();
	}

	public AdministrationStates getComponentState(UIComponent component) throws ModelException, NumberFormatException {
		AdministrationStates state = null;

		switch (this.getAction()) {
			case EDIT:
				if (null != getArticleTitle()) {
					state = AdministrationStates.EDIT;
				} else {
					state = AdministrationStates.CREATE;
				}
				break;
			case UNKNOWN:
				state = AdministrationStates.LIST;
				break;
			case VIEW:
				if (null != getArticleId()) {
					state = AdministrationStates.VIEW;
				} else {
					state = AdministrationStates.LIST;
				}
				break;
			case HISTORY_LIST:
				if (this.getArticleId() != null) {
					state = AdministrationStates.HISTORY_LIST;
				} else {
					state = AdministrationStates.LIST;
				}
				break;
			case HISTORY_VIEW:
				if (!this.wikiURLParser.getHistoryId().isEmpty()) {
					state = AdministrationStates.HISTORY_VIEW;
				} else {
					state = AdministrationStates.LIST;
				}
				break;
case IMPORT_ACL:
				state = AdministrationStates.LIST;
				break;
				case MERGE_SUGGESTION_LIST:
				state = AdministrationStates.MERGE_SUGGESTION_LIST;
				break;
			case MERGE_SUGGESTION_VIEW:
				state = AdministrationStates.MERGE_SUGGESTION_VIEW;
				break;	
			case DEAD_ARTICLE_LIST:
				state = AdministrationStates.DEAD_ARTICLE_LIST;
				break;	
			case DEAD_ARTICLE_VIEW:
				state = AdministrationStates.DEAD_ARTICLE_VIEW;
				break;
			case FORUM_TOPIC_LIST:
				state = AdministrationStates.FORUM_TOPIC_LIST;
				break;
			case FORUM_TOPIC_CREATE:
				state = AdministrationStates.FORUM_TOPIC_CREATE;
				break;
			default:
				state = AdministrationStates.LIST;
				break;
		}
		state = this.filterNotAllowedStates(state);
		return state;
	}

	public ArticleId getArticleId() throws ModelException, NumberFormatException {
		String articleStringId = wikiURLParser.getArticleId();
		if (null != articleStringId) {
			return new ArticleId(Integer.parseInt(articleStringId));
		}
		ArticleTO article = null;
		article = this.facade.findArticleByTitle(this.getArticleTitle());
		ArticleId id = null;
		if (article != null) {
			id = article.getId();
		}
		return id;
	}

	private String getArticleTitle() {
		return wikiURLParser.getArticleTitle();
	}

	public AdministrationActions getAction() {
		String urlAction = wikiURLParser.getAction();

		if (urlAction == null || urlAction.length() == 0) {
			return AdministrationActions.UNKNOWN;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.DELETE.action)) {
			return AdministrationActions.DELETE;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.LOCK.action)) {
			return AdministrationActions.LOCK;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.UNLOCK.action)) {
			return AdministrationActions.UNLOCK;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.EDIT.action)) {
			return AdministrationActions.EDIT;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.VIEW.action)) {
			return AdministrationActions.VIEW;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.HISTORY_LIST.action)) {
			return AdministrationActions.HISTORY_LIST;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.RESTORE.action)) {
			return AdministrationActions.RESTORE;
		} else if (urlAction.equalsIgnoreCase(AdministrationActions.HISTORY_VIEW.action)) {
			return AdministrationActions.HISTORY_VIEW;
		} else if(urlAction.equalsIgnoreCase(AdministrationActions.MERGE_SUGGESTION_LIST.action)){
			return AdministrationActions.MERGE_SUGGESTION_LIST;
		}else if(urlAction.equalsIgnoreCase(AdministrationActions.MERGE_SUGGESTION_VIEW.action)){
			return AdministrationActions.MERGE_SUGGESTION_VIEW;
		}else if(urlAction.equalsIgnoreCase(AdministrationActions.DEAD_ARTICLE_LIST.action)){
			return AdministrationActions.DEAD_ARTICLE_LIST;
		}else if(urlAction.equalsIgnoreCase(AdministrationActions.DEAD_ARTICLE_VIEW.action)){
			return AdministrationActions.DEAD_ARTICLE_VIEW;
		}else if(urlAction.equalsIgnoreCase(AdministrationActions.FORUM_TOPIC_LIST.action)){
			return AdministrationActions.FORUM_TOPIC_LIST;
		}else if(urlAction.equalsIgnoreCase(AdministrationActions.FORUM_TOPIC_CREATE.action)){
			return AdministrationActions.FORUM_TOPIC_CREATE;
		}else{
			return AdministrationActions.UNKNOWN;
		}
	}

	public HistoryId getHistoryId() throws ModelException {
		return new HistoryId(Integer.parseInt(this.wikiURLParser.getHistoryId()), this.getArticleId());
	}

	private AdministrationStates filterNotAllowedStates(
			AdministrationStates state) {
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
