package com.jwl.presentation.renderers;

import com.jwl.business.security.IIdentity;
import com.jwl.presentation.url.Linker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlLink;

public class EncodeAdministrationConsole extends AbstractEncoder {

	public EncodeAdministrationConsole(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add((HtmlAppForm) super.params.get("uploadACLForm"));
		components.add(encodedKnowledgeLinks());
		return components;
	}
	
	private HtmlDiv encodedKnowledgeLinks() {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass("jwl-navigation");
		
		div.addChildren(this.getShowExistingACLLink());
		div.addChildren(this.encodedMergeSuggestionsLink());
		div.addChildren(this.encodedDeadArticlesLink());
		div.addChildren(this.encodedKeyWordLink());
		
		return div;
	}
	
	private HtmlLink getShowExistingACLLink() {

		HtmlLink link = new HtmlLink();
		link.setIsAjax(Boolean.TRUE);
		link.setText("Show existing ACL");
		link.setValue(this.linker.buildLink("exportACL"));
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}

	private HtmlLink encodedMergeSuggestionsLink() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.MERGE_SUGGESTION_LIST.id);

		HtmlLink link = this.getHtmlLink("Merge suggestions", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}
	
	private HtmlLink encodedDeadArticlesLink() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.DEAD_ARTICLE_LIST.id);

		HtmlLink link = this.getHtmlLink("Dead articles suggestions", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}

	private HtmlLink encodedKeyWordLink() {
		HtmlLink link = new HtmlLink();
		link.setIsAjax(false);
		link.setText("create key words - do nothing");
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		link.setId(JWLElements.ADMINISTRATION_KW_LINK.id);
		
		return link;
	}
	
}
