package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.renderers.units.ArticleSuggestionsComponent;

public class EncodeView extends AbstractEncodeView {

	private List<ArticleTO> similarArticles;

	public EncodeView(List<ArticleTO> similarArticles) {
		this.similarArticles = similarArticles;
	}

	public EncodeView() {
		super();
	}

	@Override
	public List<UIComponent> getEncodedComponent(){
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(super.encodedArticlePanel());
		components.add(super.encodedPanelActionButtons());
		components.add(this.encodedSimilarArticles());
		return components;
	}

	protected HtmlDiv encodedSimilarArticles(){
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.SUGGESTOR_DIV);
		if(!similarArticles.isEmpty()){
			List<UIComponent> suggestorComponents = ArticleSuggestionsComponent
					.getComponent(similarArticles, context);
			div.getChildren().addAll(suggestorComponents);
		}
		return div;
	}

}
