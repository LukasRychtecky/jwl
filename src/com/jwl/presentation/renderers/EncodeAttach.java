package com.jwl.presentation.renderers;

import com.jwl.business.article.ArticleTO;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;
import java.util.HashMap;

public class EncodeAttach extends AbstractEncoder {
	public EncodeAttach(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		this.components.add((HtmlAppForm) this.params.get("attachFile"));
		HtmlLink link = new HtmlLink();
		link.setIsAjax(Boolean.TRUE);
		link.setText("Back to detail");
		
		ArticleTO article = (ArticleTO) params.get("article");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
		params.put(JWLURLParams.STATE, "view");
		link.setValue(this.linker.buildLink(params));
		
		HtmlDiv navigation = new HtmlDiv();
		navigation.addStyleClass("jwl-navigation");
		navigation.getChildren().add(link);
		this.components.add(navigation);
		return this.components;
	}
}