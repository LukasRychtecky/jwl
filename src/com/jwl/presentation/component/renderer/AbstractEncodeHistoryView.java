package com.jwl.presentation.component.renderer;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputLink;

import com.jwl.business.article.HistoryTO;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.convertor.MarkupToMarkdown;
import com.jwl.util.html.component.HtmlLinkProperties;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractEncodeHistoryView extends JWLEncoder {

	private void encodeTitle(String title) throws IOException {
		super.encodeH1Text(title, JWLStyleClass.VIEW_TITLE);
	}
	
	protected void enconde(HistoryTO history) throws IOException {
		this.encodeTitle(history.getTitle());
		this.encodeText(history.getText());
		this.encodeLinkToListing(history);
	}

	private void encodeText(String text) throws IOException {
		String html = MarkupToMarkdown.convert(text);
		super.encodeDivClassStart(JWLStyleClass.VIEW_TEXT);
		super.encodePlainText(html);
		super.encodeDivEnd();
	}

	private void encodeLinkToListing(HistoryTO history) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to listing");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.HISTORY_LIST);
		properties.addParameter(JWLURLParameters.ARTICLE_ID, history.getId().getArticleId().getId());
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_BACK);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(super.context);
	}


}
