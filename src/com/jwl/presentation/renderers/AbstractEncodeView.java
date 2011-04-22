package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.security.AccessPermissions;
import com.jwl.presentation.components.core.AbstractComponent;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.markdown.MarkupToMarkdown;
import com.jwl.presentation.renderers.units.RatingComponent;

public abstract class AbstractEncodeView extends AbstractEncoder {

	protected ArticleTO article;

	public AbstractEncodeView(ArticleTO article) {
		super();
		this.article = article;
	}
	
	protected List<UIComponent> encondedArticle() {
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(this.encodedTitle());
		components.add(this.encodedText());
		if (!article.getAttachments().isEmpty()) {
			components.add(this.encodedAttachments());
		}
		if (!article.getTags().isEmpty()) {
			components.add(this.encodedTags());
		}
		return components;
	}

	private HtmlDiv encodedTitle() {
		HtmlDiv titleDiv = new HtmlDiv();
		titleDiv.setStyleClass(JWLStyleClass.VIEW_TITLE);
		
		HtmlFreeOutput output = new HtmlFreeOutput();
		output.setFreeOutput(article.getTitle());
		
		titleDiv.addChildren(output);
		return titleDiv;
	}

	private HtmlDiv encodedText() {
		HtmlDiv textDiv = new HtmlDiv();
		textDiv.setStyleClass(JWLStyleClass.VIEW_TEXT);
		
		String html = MarkupToMarkdown.convert(article.getText());
		HtmlFreeOutput out = new HtmlFreeOutput();
		out.setFreeOutput(html);
		
		textDiv.addChildren(out);
		return textDiv;
	}

	private HtmlDiv encodedAttachments() {		
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(JWLStyleClass.VIEW_ATTACHMENTS);
		
		for (AttachmentTO attachment : article.getAttachments()) {
			Map<String, String> params = new HashMap<String, String>();
			params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
			params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
			params.put(JWLURLParams.FILE_NAME, attachment.getTitle());
			
			HtmlLink link = new HtmlLink();
			link.setValue(linker.buildLink(AbstractComponent.JWL_DOWNLOAD_FILE_PAGE, params));
			link.setText(attachment.getTitle());
			
			div.addChildren(link);
		}
		
		return div;
	}

	private HtmlDiv encodedTags() {
		Set<String> tags = article.getTags();
		HtmlDiv tagsDiv = new HtmlDiv();
		tagsDiv.setStyleClass(JWLStyleClass.VIEW_TAGS);
		for (String tag : tags) {
			// TODO Refactoring to search articles with the same tag
			/*HtmlLinkProperties properties = new HtmlLinkProperties();
			properties.addParameter(...);
			properties.setValue(tag);
			super.getHtmlLinkComponent(properties).encodeAll(context);*/
			HtmlFreeOutput output = new HtmlFreeOutput();
			output.setFreeOutput("<span>" + tag + "</span>");
		}
		return tagsDiv;
	}

	protected List<UIComponent> encodedCommonLinks(ArticleTO article) {
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(this.encodedLinkToListing());
		if (!article.isLocked()) {
			if (this.hasEditPermission(article.getId())) {
				components.add(this.encodedLinkToEdit(article.getTitle()));
			}
			if (this.hasAttachmentAddPermission(article.getId())) {
				components.add(this.encodedLinkToAttach(article.getTitle()));
			}
		}
		components.add(this.encodedLinkForum(article.getTitle()));
		return components;
	}

	protected HtmlLink encodedLinkToListing() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_LIST.id);

		HtmlLink link = getHtmlLink("Back to listing", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_BACK);
		return link;
	}

	private HtmlLink encodedLinkToEdit(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_EDIT.id);
		params.put(JWLURLParams.ARTICLE_TITLE, title);

		HtmlLink link = getHtmlLink("Edit", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_EDIT);
		return link;
	}

	private HtmlLink encodedLinkToAttach(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ATTACH_FILE.id);
		params.put(JWLURLParams.ARTICLE_TITLE, title);

		HtmlLink link = getHtmlLink("Attach file", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_ATTACH);
		return link;
	}
	
	private HtmlLink encodedLinkForum(String articleTitle) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_LIST.id);
		params.put(JWLURLParams.ARTICLE_TITLE, articleTitle);

		HtmlLink link = getHtmlLink("Forum", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_ATTACH);
		return link;
	}

	private boolean hasEditPermission(ArticleId articleId) {
		return this.hasPermission(AccessPermissions.ARTICLE_EDIT,articleId);
	}

	private boolean hasAttachmentAddPermission(ArticleId articleId) {
		return this.hasPermission(AccessPermissions.ATTACHMENT_ADD,articleId);
	}

	protected HtmlDiv encodedArticlePanel() {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		
		panel.getChildren().add(this.encodedPanelHeader());
		panel.getChildren().add(this.encodedPanelBody());
		
		return panel;
	}
	
	private UIComponent encodedPanelHeader() {
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlOutputText text = new HtmlOutputText();
		text.setValue("Wiki Article");

		panelHeader.getChildren().add(text);
		return panelHeader;
	}

	private UIComponent encodedPanelBody() {
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		
		panelBody.getChildren().addAll(this.encondedArticle());
		
		HtmlFreeOutput rating = RatingComponent.encodedRating(
					article.getRatingAverage(), article.getId());
		
		panelBody.getChildren().add(rating);
		return panelBody;
	}
	
	protected HtmlDiv encodedPanelActionButtons() {
		HtmlDiv panelButtons = new HtmlDiv();
		panelButtons.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		
		panelButtons.addChildren(this.encodedCommonLinks(article));
		
		return panelButtons;
	}
	
		
}