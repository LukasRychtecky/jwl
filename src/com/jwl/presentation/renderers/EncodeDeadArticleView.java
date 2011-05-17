package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;

public class EncodeDeadArticleView extends AbstractEncodeView {

	public EncodeDeadArticleView(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(super.encodedArticlePanel());
		components.add(super.encodedPanelActionButtons());
		return components;
	}

	@Override
	protected HtmlLink encodedLinkToListing() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.DEAD_ARTICLE_LIST.id);

		HtmlLink link = getHtmlLink("Back to listing", params);
		return link;
	}

}
