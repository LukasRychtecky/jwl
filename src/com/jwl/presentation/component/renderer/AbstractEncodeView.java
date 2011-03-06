package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.Set;
import javax.faces.component.html.HtmlOutputLink;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.article.enumerations.ArticlePermissions;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.convertor.MarkupToMarkdown;
import com.jwl.util.html.component.HtmlLinkProperties;

public abstract class AbstractEncodeView extends JWLEncoder {

	public AbstractEncodeView(IFacade facade) {
		super(facade);
	}

	protected void encondeArticle(ArticleTO article) throws IOException,
			ModelException {
		this.encodeTitle(article.getTitle());
		this.encodeText(article.getText());
		this.encodeTags(super.facade.getArticle(article.getId()).getTags());

	}

	private void encodeTitle(String title) throws IOException {
		super.encodeH1Text(title, JWLStyleClass.VIEW_TITLE);
	}

	private void encodeText(String text) throws IOException {
		String html = MarkupToMarkdown.convert(text);
		super.encodeDivClassStart(JWLStyleClass.VIEW_TEXT);
		super.encodePlainText(html);
		super.encodeDivEnd();
	}

	private void encodeTags(Set<String> tags) throws IOException {
		super.encodeDivClassStart(JWLStyleClass.VIEW_TAGS);
		super.getHtmlTextComponent(tags.toString()).encodeAll(context);
		super.encodeDivEnd();
	}

	protected void encodeCommonLinks(ArticleTO article) throws IOException {
		this.encodeLinkToListing();
		if (!article.isLocked()) {
			if (this.hasEditPermission(article.getId())) {
				this.encodeLinkToEdit(article.getTitle());
			}
			if (this.hasAttachmentAddPermission(article.getId())) {
				this.encodeLinkToAttach(article.getTitle());
			}
		}
	}

	private void encodeLinkToListing() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to listing");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_BACK);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(context);
	}

	private void encodeLinkToEdit(String title) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Edit");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.EDIT);
		properties.addParameter(JWLURLParameters.ARTICLE_TITLE, title);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_EDIT);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(context);
	}

	private void encodeLinkToAttach(String title) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Attach file");
		properties.addParameter(JWLURLParameters.ACTION,
				ArticleActions.ATTACH_FILE);
		properties.addParameter(JWLURLParameters.ARTICLE_TITLE, title);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_ATTACH);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(context);
	}

	private boolean hasEditPermission(ArticleId articleId) {
		return this.hasPermission(ArticlePermissions.ARTICLE_EDIT, articleId);
	}

	private boolean hasAttachmentAddPermission(ArticleId articleId) {
		return this.hasPermission(ArticlePermissions.ATTACHMENT_ADD, articleId);
	}

	protected void encodeRating(float ratingAverage, ArticleId articleId)
			throws IOException {
		int sn = (int) ratingAverage;
		int r = (int) (ratingAverage * 10) % 1;
		if (r >= 5) {
			sn++;
		}

		writer.write("<form action=\"\">");
		super.encodeDivClassStart("stars");
		for (int i = 1; i < 11; i++) {
			float sv = (float) i / 2;
			if (i == sn) {
				encodeStar(i, sv, true);
			} else {
				encodeStar(i, sv, false);
			}
		}
		super.encodeDivEnd();
		writer.write("<input id=\"rating-article-id\" type=\"hidden\" value=\""
				+ articleId.getId().intValue() + "\" />");
		writer.write("</form>");
	}

	private void encodeStar(int elementNumber, float starValue, boolean checked)
			throws IOException {
		writer.write("<label for=\"rating-" + elementNumber + "\">");
		writer.write("<input id=\"rating-" + elementNumber
				+ "\" name=\"rating\" type=\"radio\" value=\"" + elementNumber
				+ "\"");
		if (checked) {
			writer.write(" checked");
		}
		writer.write("\"/>");
		writer.write(starValue + " stars");
		writer.write("</label>");
	}

}
