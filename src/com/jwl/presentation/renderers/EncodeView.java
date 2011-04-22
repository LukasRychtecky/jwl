package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlLink;

public class EncodeView extends AbstractEncodeView {

	private List<ArticleTO> similarArticles;
	
	public EncodeView(ArticleTO article, List<ArticleTO> similarArticles) {
		super(article);
		this.similarArticles = similarArticles;
	}
	
	public EncodeView(ArticleTO article) {
		super(article);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(super.encodedArticlePanel());
		components.add(super.encodedPanelActionButtons());
		//components.add(this.encodedSimilarArticles());
		return components;
	}

	protected HtmlDiv encodedSimilarArticles() {
		HtmlDiv div = new HtmlDiv();
		div.setId(JWLElements.VIEW_SIMILAR_ARTICLE_DIV.id);
		
		if (!similarArticles.isEmpty()) {
			div.addChildren(getHtmlText("Possibly similar articles:"));
			for (ArticleTO sa : similarArticles) {
				div.addChildren(this.encodeArticleLinkComponent(sa.getTitle()));
			}
		}
		
		return div;
	}
	
	private HtmlLink encodeArticleLinkComponent(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, title);
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		// properties.addClass(JWLStyleClass.ACTION_BUTTON);
		return this.getHtmlLink(title, params);
	}
	
}
