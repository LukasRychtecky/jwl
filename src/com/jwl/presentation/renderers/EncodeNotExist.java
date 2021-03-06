package com.jwl.presentation.renderers;

import com.jwl.business.security.IIdentity;
import com.jwl.presentation.url.Linker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlLink;


public class EncodeNotExist extends AbstractEncoder {

	public EncodeNotExist(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(getHtmlText("Article does not exist! "));
		components.add(getLinkToCreate("Do you want create it?"));
		return components;
	}

	private HtmlLink getLinkToCreate(String label) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_EDIT.id);
		return this.getHtmlLink(label, params);
	}

}
