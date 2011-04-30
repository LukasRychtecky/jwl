package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlHeadline;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;
import java.util.HashMap;
import java.util.Map;

public class EncodeEdit extends AbstractEncoder {

	private ArticleTO article;
	private HtmlAppForm form;
	
	public EncodeEdit(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.article = (ArticleTO) params.get("article");
		this.form = (HtmlAppForm) params.get("form");
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		
		HtmlHeadline headline = new HtmlHeadline(1);
		headline.setText(this.article.getTitle());
		components.add(headline);
		components.add(this.form);
		
		HtmlLink link = new HtmlLink();
		link.setText("Back to detail");
		link.setIsAjax(Boolean.TRUE);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		params.put(JWLURLParams.STATE, "view");
		link.setValue(this.linker.buildLink(params));
		components.add(link);
		return components;
	}	
}
