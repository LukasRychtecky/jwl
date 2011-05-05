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
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodedTitle(history.getTitle()));
		components.add(this.encodedText(history.getText()));
		components.add(this.encodedLinkToListing());
		return components;
	}
	
	private HtmlDiv encodedTitle(String title) {
		HtmlDiv titleDiv = new HtmlDiv();
		titleDiv.addStyleClass(JWLStyleClass.VIEW_TITLE);
		
		HtmlFreeOutput output = new HtmlFreeOutput();
		output.setFreeOutput(title);
		titleDiv.addChildren(output);
		
		return titleDiv;
	}
	
	private HtmlDiv encodedText(String text) {
		HtmlDiv textDiv = new HtmlDiv();
		textDiv.addStyleClass(JWLStyleClass.VIEW_TEXT);
		
		String html = MarkupToMarkdown.convert(text);
		HtmlFreeOutput output = new HtmlFreeOutput();
		output.setFreeOutput(html);
		
		textDiv.addChildren(output);
		
		return textDiv;
	}

	private HtmlLink encodedLinkToListing() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.HISTORY_LIST.id);
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());

		HtmlLink link = getHtmlLink("Back to listing", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_BACK);
		return link;
	}

}
