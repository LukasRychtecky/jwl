package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlHeadline;

public class EncodeEdit extends AbstractEncoder {

	private ArticleTO article;
	private HtmlAppForm form;
	
	public EncodeEdit(ArticleTO article, HtmlAppForm form) {
		super();
		this.article = article;
		this.form = form;
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
