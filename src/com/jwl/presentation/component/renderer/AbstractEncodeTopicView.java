package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.convertor.MarkupToMarkdown;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDiv;
import com.jwl.util.html.component.HtmlFreeOutput;
import com.jwl.util.html.component.HtmlLinkProperties;

public abstract class AbstractEncodeTopicView extends JWLEncoder {

	public AbstractEncodeTopicView(IFacade facade) {
		super(facade);
	}

	protected void encodeTopicPanel(TopicTO topic) throws IOException,
			ModelException {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Article topic");
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		List<UIComponent> bodyChildren = panelBody.getChildren();
		bodyChildren
				.add(getInitialPostComponent(topic.getOpeningPost(), topic));
		List<UIComponent> panelChildern = panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		panel.encodeAll(context);
	}

	protected void encodeTopicPanelActionButtons(TopicTO topic,
			ArticleId articleId) throws IOException {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(getArticleLinkComponent(articleId));
		panelChildren.add(getTopicListLink(articleId));
		if (!topic.isClosed()&&hasPostReplyPermission()) {
			panelChildren.add(getReplyLinkComponent());
			panelChildren.add(getQuoteLinkComponent(topic.getOpeningPost()
					.getId()));
		}
		buttonsPanel.encodeAll(context);
	}

	protected void encodeReplyViewPanel(List<PostTO> replies, boolean adminMode, boolean topicLocked)
			throws IOException, ModelException {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue(replies.size() + " replies");
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.getChildren().add(getResponses(replies, adminMode, topicLocked));
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		List<UIComponent> panelChildern = panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		panel.encodeAll(context);
	}

	protected HtmlActionForm getAdministrationForm(Integer postId) {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + "-"
				+ postId.toString());
		form.setStyleClass(JWLStyleClass.FORUM_ACTIONS_FORM);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.getAdminFormAction());
		form.getChildren().add(getDeleteButton());
		return form;
	}

	protected UIComponent getResponses(List<PostTO> replies, boolean adminMode, boolean topicLocked)
			throws IOException {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_REPLIES);
		table.setColumns(1);
		List<UIComponent> tableData = table.getChildren();
		for (PostTO reply : replies) {
			tableData.add(getReplyComponent(reply, adminMode, topicLocked));
		}
		return table;
	}

	protected UIComponent getInitialPostComponent(PostTO post, TopicTO topic)
			throws IOException {
		HtmlDiv component = new HtmlDiv();
		component.setStyleClass(JWLStyleClass.FORUM_INITIAL_POST);
		List<UIComponent> componentChildren = component.getChildren();
		componentChildren.add(getPostHeader(topic.getTitle(), post.getAuthor(),
				post.getCreated(), topic.isClosed()));
		componentChildren.add(getPostTextComponet(post.getText()));
		return component;
	}

	protected UIComponent getReplyComponent(PostTO post, boolean adminMode, boolean topicLocked)
			throws IOException {
		HtmlDiv component = new HtmlDiv();
		component.setStyleClass(JWLStyleClass.FORUM_INITIAL_POST);
		List<UIComponent> componentChildren = component.getChildren();
		componentChildren.add(getReplyHeader(post.getAuthor(),
				post.getCreated()));
		componentChildren.add(getPostTextComponet(post.getText()));
		componentChildren.add(getReplyActionsComponent(post, adminMode, topicLocked));
		return component;
	}

	protected UIComponent getPostHeader(String title, String author, Date date,
			boolean topicLocked) throws IOException {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_POST_HEADER);
		table.setColumns(2);
		table.setColumnClasses(JWLStyleClass.FORUM_POST_HEADER_TITLE + ","
				+ JWLStyleClass.FORUM_POST_AUTHOR);
		HtmlFreeOutput titleComp = new HtmlFreeOutput();
		titleComp.setFreeOutput("");
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(title);
		titleComp.getChildren().add(text);
		if (topicLocked) {
			titleComp.getChildren().add(
					getImageComponent("/SeamWiki/jwl/lock.png", 16, 16));
		}
		table.getChildren().add(titleComp);
		HtmlFreeOutput authorComp = new HtmlFreeOutput();
		HtmlDiv dateDiv = new HtmlDiv();
		dateDiv.setValue(date.toString());
		authorComp.getChildren().add(dateDiv);
		HtmlDiv authorDiv = new HtmlDiv();
		authorDiv.setValue(author);
		authorComp.getChildren().add(authorDiv);
		table.getChildren().add(authorComp);
		return table;
	}

	protected UIComponent getReplyHeader(String author, Date date) {
		HtmlDiv component = new HtmlDiv();
		component.setStyleClass(JWLStyleClass.FORUM_POST_AUTHOR);
		HtmlDiv dateDiv = new HtmlDiv();
		dateDiv.setValue(date.toString());
		component.getChildren().add(dateDiv);
		HtmlDiv authorDiv = new HtmlDiv();
		authorDiv.setValue(author);
		component.getChildren().add(authorDiv);
		return component;
	}

	protected UIComponent getPostTextComponet(String text) {
		HtmlDiv div = new HtmlDiv();
		HtmlFreeOutput out = new HtmlFreeOutput();
		String html = MarkupToMarkdown.convert(text);
		out.setFreeOutput(html);
		div.getChildren().add(out);
		return div;
	}

	protected UIComponent getReplyActionsComponent(PostTO post,
			boolean adminMode, boolean topicLocked) {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(JWLStyleClass.FORUM_REPLY_ACTIONS);
		List<UIComponent> divChildren = div.getChildren();
		if (!topicLocked&&hasPostReplyPermission()){
			divChildren.add(getReplyLinkComponent());
			divChildren.add(getQuoteLinkComponent(post.getId()));
		}
		if (adminMode&&hasDeletePostPermission()){
			divChildren.add(getAdministrationForm(post.getId()));
		}
		return div;
	}

	protected UIComponent getArticleLinkComponent(ArticleId articleId) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to article");
		properties.addParameter(JWLURLParameters.ARTICLE_ID, articleId.getId());
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.VIEW);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return this.getHtmlLinkComponent(properties);
	}

	protected UIComponent getTopicListLink(ArticleId id) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Topic list");
		properties.addParameter(JWLURLParameters.ACTION,
				ArticleActions.FORUM_TOPIC_LIST);
		properties.addParameter(JWLURLParameters.ARTICLE_ID, id.getId());
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_ATTACH);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		return link;
	}

	protected UIComponent getReplyLinkComponent() {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Reply");
		WikiURLParser parser = new WikiURLParser();
		for (Entry<String, String> e : parser.getURLParameters().entrySet()) {
			properties.addParameter(e.getKey(), e.getValue());
		}
		properties.addParameter(JWLURLParameters.ANSWERING, "yes");
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return this.getHtmlLinkComponent(properties);
	}

	protected UIComponent getQuoteLinkComponent(Integer postId) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Quote");
		WikiURLParser parser = new WikiURLParser();
		for (Entry<String, String> e : parser.getURLParameters().entrySet()) {
			properties.addParameter(e.getKey(), e.getValue());
		}
		properties.addParameter(JWLURLParameters.ANSWERING, "yes");
		properties.addParameter(JWLURLParameters.QUOTE_POST_ID,
				postId.toString());
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return this.getHtmlLinkComponent(properties);
	}

	private UIComponent getImageComponent(String imgSrc, int imgWidth,
			int imgHeight) throws IOException {
		HtmlFreeOutput component = new HtmlFreeOutput();
		component.setFreeOutput(encodeImage(imgSrc, imgWidth, imgHeight));
		return component;
	}

	private String encodeImage(String imgSrc, int imgWidth, int imgHeight)
			throws IOException {
		return "<img width =\"" + imgWidth + "\" height =\"" + imgHeight
				+ "\" src=\"" + imgSrc + "\" />";
	}

	protected String getAdminFormAction() {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String target = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParameters();
		return getFormActionString(context, target, params);
	}

	protected UIComponent getDeleteButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId("delete");
		button.setValue("delete");
		button.setType("submit");
		button.setStyleClass(JWLStyleClass.FORUM_REPLIES_DELETE);
		return button;
	}

	protected void encodeReplyForm(TopicTO topic, PostTO quotedPost)
			throws IOException, ModelException {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_POST_REPLY_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.getReplyFormAction());
		List<UIComponent> formData = form.getChildren();
		formData.add(getReplyFormPanel(topic, quotedPost));
		formData.add(getReplyFormActionButtons(topic));
		form.encodeAll(context);
	}

	protected String getReplyFormAction() {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String target = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParameters();
		params.remove(JWLURLParameters.ANSWERING);
		params.remove(JWLURLParameters.QUOTE_POST_ID);
		return getFormActionString(context, target, params);
	}

	protected UIComponent getReplyButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		button.setType("submit");
		button.setId(JWLElements.FORUM_POST_REPLY_FORM.id + "-"
				+ JWLElements.FORUM_POST_REPLY.id);
		button.setValue(JWLElements.FORUM_POST_REPLY.value);
		return button;
	}

	protected UIComponent getReplyCancelButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		// button.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		button.setType("submit");
		button.setId(JWLElements.FORUM_POST_REPLY_FORM.id + "-"
				+ JWLElements.FORUM_POST_CANCEL.id);
		button.setValue(JWLElements.FORUM_POST_CANCEL.value);
		return button;
	}

	protected UIComponent getHiddenTopicId(Integer topicId) {
		HtmlInputHidden hiddenTopicId = new HtmlInputHidden();
		hiddenTopicId.setId(JWLElements.FORUM_POST_REPLY_FORM.id + "-"
				+ JWLElements.FORUM_POST_TOPIC_ID.id);
		hiddenTopicId.setValue(topicId.toString());
		return hiddenTopicId;
	}

	protected UIComponent getReplyText(String initText) throws IOException {
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		textArea.setRows(15);
		textArea.setCols(40);
		textArea.setValue(initText);
		textArea.setId(JWLElements.FORUM_POST_REPLY_FORM.id + "-"
				+ JWLElements.FORUM_POST_TEXT.id);
		return textArea;
	}

	protected UIComponent getReplyFormPanel(TopicTO topic, PostTO quotedPost)
			throws IOException, ModelException {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Post reply");
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		List<UIComponent> bodyChildren = panelBody.getChildren();
		bodyChildren.add(getReplyText(getInitText(quotedPost)));
		bodyChildren.add(getHiddenTopicId(topic.getId()));
		List<UIComponent> panelChildern = panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		return panel;
	}

	protected UIComponent getReplyFormActionButtons(TopicTO topic)
			throws IOException {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(getReplyButton());
		panelChildren.add(getReplyCancelButton());
		return buttonsPanel;
	}

	protected String getInitText(PostTO quotedPost) {
		if (quotedPost == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(">");
		sb.append(quotedPost.getAuthor());
		sb.append(" wrote on ");
		sb.append(quotedPost.getCreated().toString());
		sb.append(": <br/>");
		sb.append(System.getProperty("line.separator"));
		sb.append(convertToQuoted(quotedPost.getText()));
		return sb.toString();
	}

	private String convertToQuoted(String text) {
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		int prevLineEnd = 0;
		int lineEnd = text.indexOf(lineSeparator);
		while (lineEnd != -1) {
			lineEnd +=lineSeparator.length();
			sb.append(">");
			sb.append(text.substring(prevLineEnd, lineEnd));
			prevLineEnd = lineEnd;
			lineEnd = text.indexOf(lineSeparator, prevLineEnd);
		}
		sb.append(">");
		sb.append(text.substring(prevLineEnd));
		return sb.toString();
	}
	
	private boolean hasPostReplyPermission() {
		return this.hasPermission(AccessPermissions.FORUM_ADD_POST);
	}
	
	private boolean hasDeletePostPermission() {
		return this.hasPermission(AccessPermissions.FORUM_DELETE_POST);
	}

}
