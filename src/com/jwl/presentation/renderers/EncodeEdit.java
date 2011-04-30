package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlHeadline;
import com.jwl.presentation.url.Linker;
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
		return components;
	}	
}
