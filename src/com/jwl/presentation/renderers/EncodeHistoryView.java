package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlHeadline;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.markdown.MarkupToMarkdown;
import com.jwl.presentation.url.Linker;

/**
 *
 * @author Lukas Rychtecky
 */
public class EncodeHistoryView extends AbstractEncoder {

	private ArticleTO article;
	private HistoryTO history;

	public EncodeHistoryView(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
		this.history = (HistoryTO) params.get("history");
	}

	@Override
	public List<UIComponent> getEncodedComponent() {		
		HtmlDiv container = new HtmlDiv();
		container.addStyleClass("jwl-article");
		container.getChildren().add(this.encodedTitle(history.getTitle()));
		container.getChildren().add(this.encodedText(history.getText()));
		container.getChildren().add(this.encodedLinkToListing());
		this.components.add(container);
		return this.components;
	}
	
	private HtmlHeadline encodedTitle(String title) {
		HtmlHeadline headline = new HtmlHeadline(1);
		headline.setText(title);		
		return headline;
	}
	
	private HtmlDiv encodedText(String text) {
		HtmlDiv textDiv = new HtmlDiv();
		textDiv.addStyleClass("jwl-article-text");
		
		String html = MarkupToMarkdown.convert(text);
		HtmlFreeOutput output = new HtmlFreeOutput();
		output.setFreeOutput(html);		
		textDiv.addChildren(output);		
		return textDiv;
	}

	private HtmlDiv encodedLinkToListing() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.HISTORY_LIST.id);
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
		
		HtmlDiv navigation = new HtmlDiv();
		navigation.addStyleClass("jwl-navigation");

		HtmlLink link = getHtmlLink("Back to listing", params);
		link.setIsAjax(Boolean.TRUE);
		navigation.getChildren().add(link);
		return navigation;
	}

}
