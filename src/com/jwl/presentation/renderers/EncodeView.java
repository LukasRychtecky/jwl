package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.renderers.units.ArticleSuggestionsComponent;
import com.jwl.presentation.url.Linker;
import java.util.Map;

public class EncodeView extends AbstractEncodeView {

	private List<ArticleTO> similarArticles;

	@SuppressWarnings("unchecked")
	public EncodeView(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.similarArticles = (List<ArticleTO>) params.get("similarArticles");
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
