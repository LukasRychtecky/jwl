package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.faces.component.html.HtmlOutputLink;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLElements;
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
		this.encodeAttachments(article.getAttachments());
		this.encodeTags(article.getTags());
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
		if (!tags.isEmpty()) {
			super.encodeDivClassStart(JWLStyleClass.VIEW_TAGS);
			for (String tag : tags) {
				// TODO Refactoring to search articles with the same tag
				/*HtmlLinkProperties properties = new HtmlLinkProperties();
				properties.addParameter(...);
				properties.setValue(tag);
				super.getHtmlLinkComponent(properties).encodeAll(context);*/
				super.encodePlainText("<span>" + tag + "</span>");
			}
			super.encodeDivEnd();
		}
	}
	
	private void encodeAttachments(Set<AttachmentTO> attachments) throws IOException {
		if (!attachments.isEmpty()) {
			super.encodeDivClassStart(JWLStyleClass.VIEW_ATTACHMENTS);
			for (AttachmentTO att : attachments) {
				HtmlLinkProperties properties = new HtmlLinkProperties();
				//properties.addParameter(JWLURLParameters.FILE_ACTION, att.getUniqueName());
				properties.setValue(att.getTitle());
				super.getHtmlLinkComponent(properties).encodeAll(context);
			}
			super.encodeDivEnd();
		}
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
		this.encodeLinkForum(article.getId());
	}

	protected void encodeLinkToListing() throws IOException {
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
	
	private void encodeLinkForum(ArticleId id) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Forum");
		properties.addParameter(JWLURLParameters.ACTION,
				ArticleActions.FORUM_TOPIC_LIST);
		properties.addParameter(JWLURLParameters.ARTICLE_ID, id.getId());
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_ATTACH);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(context);
	}

	private boolean hasEditPermission(ArticleId articleId) {
	return this.hasPermission(AccessPermissions.ARTICLE_EDIT,articleId);
	}

	private boolean hasAttachmentAddPermission(ArticleId articleId) {
		return this.hasPermission(AccessPermissions.ATTACHMENT_ADD,articleId);
	}

	protected void encodeRating(float ratingAverage, ArticleId articleId)
			throws IOException {
		int sn = (int) ratingAverage;
		int r = (int) (ratingAverage % 1) * 10;
		if (r >= 5) {
			sn++;
		}

		writer.write("<form action=\"\">");
		super.encodeDivClassStart(JWLStyleClass.VIEW_STARS);
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

	protected void encodeSimilarArticles(ArticleTO article) throws IOException,
			ModelException {
		encodeDivIdStart(JWLElements.VIEW_SIMILAR_ARTICLE_DIV.id);
		List<ArticleTO> similarArticles = facade
				.getSimilarArticlesInView(article);
		if (!similarArticles.isEmpty()) {
			writer.write("Possibly similar articles:");
			writer.write("<ul>");
			for (ArticleTO sa : similarArticles) {
				writer.write("<li>");
				encodeArticleLinkComponent(sa.getTitle());
				writer.write("</li>");
			}
			writer.write("</ul>");
		}
		encodeDivEnd();
	}

	private void encodeArticleLinkComponent(String title) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(title);
		properties.addParameter(JWLURLParameters.ARTICLE_TITLE, title);
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.VIEW);
		// properties.addClass(JWLStyleClass.ACTION_BUTTON);
		this.getHtmlLinkComponent(properties).encodeAll(context);
	}
	
	protected void encodeArticlePanel(ArticleTO article) throws IOException, ModelException{
		encodeDivClassStart(JWLStyleClass.PANEL);
		encodeDivClassStart(JWLStyleClass.PANEL_HEADER);
		encodePlainText("Wiki Article");
		encodeDivEnd();
		encodeDivClassStart(JWLStyleClass.PANEL_BODY);
		encondeArticle(article);
		encodeRating(article.getRatingAverage(), article.getId());
		encodeDivEnd();
		encodeDivEnd();		
	}
	
	protected void encodePanelActionButtons(ArticleTO article) throws IOException{
		encodeDivClassStart(JWLStyleClass.PANEL_ACTION_BUTTONS);
		encodeCommonLinks(article);
		encodeDivEnd();
	}
	
		
}
