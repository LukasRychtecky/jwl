package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.html.HtmlOutputLink;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.administration.enumerations.AdministrationActions;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.component.renderer.AbstractEncodeView;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.util.html.component.HtmlLinkProperties;

public class EncodeDeadArticleView extends AbstractEncodeView {

	protected ArticleId id;

	public EncodeDeadArticleView(ArticleId id) {
		this.id = id;
	}

	@Override
	protected void encodeResponse() {
		try {
			super.encodeFlashMessages();
			ArticleTO article = this.facade.getArticle(this.id);
			super.encodeArticlePanel(article);
			super.encodePanelActionButtons(article);
		} catch (IOException e) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE,
					null, e);
			super.addImplicitErrorFlashMessage();
		} catch (ModelException e) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE,
					null, e);
			super.addImplicitErrorFlashMessage();
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	@Override
	protected void encodeLinkToListing() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to listing");
		properties.addParameter(JWLURLParameters.ACTION,
				AdministrationActions.DEAD_ARTICLE_LIST.action);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_BACK);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(context);
	}

}
