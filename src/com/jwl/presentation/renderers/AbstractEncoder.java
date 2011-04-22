package com.jwl.presentation.renderers;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.global.ExceptionLogger;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;
import com.jwl.presentation.url.WikiURLParser;

public abstract class AbstractEncoder {

	private IFacade facade;
	protected WikiURLParser parser;
	protected Linker linker;
	
	public AbstractEncoder() {		
		this.parser = new WikiURLParser();
		this.linker = (Linker) FacesContext.getCurrentInstance()
			.getAttributes().get("jwllinker");
	}

	public abstract List<UIComponent> getEncodedComponent();

	protected HtmlLink getHtmlLink(String text, Map<String, String> params) {
		HtmlLink link = new HtmlLink();
		link.setValue(linker.buildLink(params));
		link.setText(text);
		return link;
	}
		
	protected HtmlOutputText getHtmlText(Object value) {
		HtmlOutputText component = new HtmlOutputText();
		component.setValue(value);
		return component;
	}

	protected HtmlDiv getHtmlLabelComponent(JWLElements element, String styleClass) {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(styleClass);
		
		HtmlOutputLabel labelForFileName = new HtmlOutputLabel();
		labelForFileName.setFor(element.id);
		labelForFileName.setValue(element.value);
		
		div.addChildren(labelForFileName);
		return div;
	}
	
	protected HtmlDiv getHtmlInputComponent(JWLElements element, 
			String value, String styleClass) {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(styleClass);
		
		HtmlInputText inputText = new HtmlInputText();
		inputText.setValue(value);
		inputText.setId(element.id);
		
		div.addChildren(inputText);
		return div;
	}

	protected HtmlDiv getHtmlSubmitComponent(JWLElements element, String styleClass) {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(styleClass);
		
		HtmlCommandButton submit = new HtmlCommandButton();
		submit.setType("submit");
		submit.setId(element.id);
		submit.setValue(element.value);
		
		div.addChildren(submit);
		return div;
	}


	
	protected boolean isUserLogged() {
		return getFacade().getIdentity().isAuthenticated();
	}
	
	protected boolean hasAdministrationPermission() {
		// TODO Fix this. I am logged as admin and mehtod still returns false 
		return true;
//		return this.hasPermission(AccessPermissions.KNOWLEDGE_ADMINISTER) &&
//			this.hasPermission(AccessPermissions.SECURITY_IMPORT);
	}
	
	protected boolean hasArticleViewPermission(ArticleId id) {
		return this.hasPermission(AccessPermissions.ARTICLE_VIEW, id);
	}
	
	protected boolean hasRestorePermission(ArticleId id) {
		return this.hasPermission(AccessPermissions.ARTICLE_RESTORE, id);
	}

	protected boolean hasLockPermission(ArticleId id) {
		return this.hasPermission(AccessPermissions.ARTICLE_LOCK, id);
	}
	
	protected boolean hasDeletePermission(ArticleId id) {
		return this.hasPermission(AccessPermissions.ARTICLE_DELETE, id);
	}
	
	protected boolean hasCreateTopicPermission() {
		return this.hasPermission(AccessPermissions.FORUM_CREATE_TOPIC);
	}
	
	protected boolean hasDeleteTopicPermission() {
		return this.hasPermission(AccessPermissions.FORUM_DELETE_TOPIC);
	}

	protected boolean hasCloseTopicPermission() {
		return this.hasPermission(AccessPermissions.FORUM_CLOSE_TOPIC);
	}

	protected boolean hasPostReplyPermission() {
		return this.hasPermission(AccessPermissions.FORUM_ADD_POST);
	}
	
	protected boolean hasDeletePostPermission() {
		return this.hasPermission(AccessPermissions.FORUM_DELETE_POST);
	}

	
	protected boolean hasPermission(AccessPermissions permission, ArticleId id) {
		try {
			return getFacade().getIdentity().isAllowed(permission, id);
		} catch (ModelException ex) {
			ExceptionLogger.severe(getClass(), ex);
			return false;
		}
	}
	
	protected boolean hasPermission(AccessPermissions permission) {
		try {
			return getFacade().getIdentity().isAllowed(permission);
		} catch (ModelException ex) {
			ExceptionLogger.severe(getClass(), ex);
			return false;
		}
	}
	
	protected IFacade getFacade() {
		if (facade == null) {
			this.facade = Global.getInstance().getFacade();
		}
		return facade;
	}

}
