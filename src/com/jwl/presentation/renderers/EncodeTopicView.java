package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.markdown.MarkupToMarkdown;

public class EncodeTopicView extends AbstractEncoder {

	protected ArticleTO article;
	private TopicTO topic;
	protected boolean answering;
	protected Integer quotePostId;

	private final String png_lock = "/jwl/lock.png";
	
	public EncodeTopicView(boolean answering, Integer quotePostId) throws ModelException {
		super();
		Integer topicId = (Integer) this.context.getAttributes().get(JWLContextKey.TOPIC_ID);
		this.topic = this.getFacade().getTopic(topicId);
		this.article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
		
		this.answering = answering;
		this.quotePostId = quotePostId;
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodedTopic(topic));
		components.add(this.encodedActionButtons(topic, article));
		
		List<PostTO> replies = topic.getPosts();
		PostTO initialPost = replies.get(0);
		replies.remove(0);
		components.add(this.encodedReply(replies, false, topic.isClosed()));
		replies.add(initialPost);
		if (answering) {
			PostTO quotedPost = null;
			if (quotePostId != null) {
				for (PostTO post : replies) {
					if (post.getId() == quotePostId.intValue()) {
						quotedPost = post;
					}
				}
			}
			components.add(this.encodedReplyForm(topic, quotedPost));
		}
		return components;
	}
	
	protected HtmlDiv encodedTopic(TopicTO topic)  {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		
		HtmlOutputText title = getHtmlText("Article topic");
		title.setStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlDiv body = new HtmlDiv();
		body.setStyleClass(JWLStyleClass.PANEL_BODY);
		body.addChildren(getInitialPostComponent(topic.getOpeningPost(), topic));
		
		panel.addChildren(title);
		panel.addChildren(body);
		return panel;
	}


	protected UIComponent getInitialPostComponent(PostTO post, TopicTO topic)  {
		HtmlDiv component = new HtmlDiv();
		component.setStyleClass(JWLStyleClass.FORUM_INITIAL_POST);
		component.addChildren(getPostHeader(topic, post));
		component.addChildren(getPostTextComponet(post.getText()));
		return component;
	}

	
	protected HtmlDiv encodedActionButtons(TopicTO topic, ArticleTO article) {
		HtmlDiv actionButtons = new HtmlDiv();
		actionButtons.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		
		actionButtons.addChildren(getArticleLinkComponent(article.getTitle()));
		actionButtons.addChildren(getTopicListLink(article.getTitle()));
		
		if (!topic.isClosed() && hasPostReplyPermission()) {
			actionButtons.addChildren(getReplyLinkComponent());
			actionButtons.addChildren(getQuoteLinkComponent(topic.getOpeningPost().getId()));
		}
		return actionButtons;
	}

	protected HtmlDiv encodedReply(List<PostTO> replies, boolean adminMode, 
			boolean topicLocked) {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		
		HtmlOutputText title = getHtmlText(replies.size() + " replies");
		title.setStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlDiv body = new HtmlDiv();
		body.setStyleClass(JWLStyleClass.PANEL_BODY);
		body.getChildren().add(getResponses(replies, adminMode, topicLocked));
		
		panel.addChildren(title);
		panel.addChildren(body);
		return panel;
	}

	protected HtmlActionForm getAdministrationForm(Integer postId) {
		// TODO PJ Doesn't we should set some parameters to form action
		Map<String, String> params = new HashMap<String, String>();
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + "-" + postId.toString());
		form.setStyleClass(JWLStyleClass.FORUM_ACTIONS_FORM);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));
		form.getChildren().add(getDeleteButton());
		return form;
	}

	protected UIComponent getResponses(List<PostTO> replies, boolean adminMode,
			boolean topicLocked) {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_REPLIES);
		table.setColumns(1);
		List<UIComponent> tableData = table.getChildren();
		for (PostTO reply : replies) {
			tableData.add(getReplyComponent(reply, adminMode, topicLocked));
		}
		return table;
	}

	protected UIComponent getReplyComponent(PostTO post, boolean adminMode,
			boolean topicLocked) {
		HtmlDiv component = new HtmlDiv();
		component.setStyleClass(JWLStyleClass.FORUM_INITIAL_POST);
		component.addChildren(getReplyHeader(post.getAuthor(), post.getCreated()));
		component.addChildren(getPostTextComponet(post.getText()));
		component.addChildren(getReplyActionsComponent(post, adminMode, topicLocked));
		return component;
	}

	protected UIComponent getPostHeader(TopicTO topic, PostTO post)  {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_POST_HEADER);
		table.setColumns(3);
		table.setColumnClasses(
				JWLStyleClass.FORUM_POST_HEADER_TITLE + "," +
				JWLStyleClass.FORUM_POST_HEADER_TITLE + "," +
				JWLStyleClass.FORUM_POST_AUTHOR);
		
		HtmlDiv titleDiv = new HtmlDiv();
		HtmlOutputText title = getHtmlText(topic.getTitle());
		titleDiv.addChildren(title);
		if (topic.isClosed()) {
			titleDiv.addChildren(getImageComponent(png_lock, 16, 16));
		}
		
		HtmlOutputText author = getHtmlText(post.getAuthor());
		HtmlOutputText date = getHtmlText(post.getCreated().toString());
		
		table.getChildren().add(titleDiv);
		table.getChildren().add(author);
		table.getChildren().add(date);
		return table;
	}

	protected UIComponent getReplyHeader(String author, Date date) {
		HtmlDiv component = new HtmlDiv();
		component.setStyleClass(JWLStyleClass.FORUM_POST_AUTHOR);
		
		HtmlOutputText dateDiv = getHtmlText(date.toString());
		HtmlOutputText authorDiv = getHtmlText(author);
		
		component.addChildren(dateDiv);
		component.addChildren(authorDiv);
		return component;
	}

	protected UIComponent getPostTextComponet(String text) {
		HtmlDiv div = new HtmlDiv();

		String html = MarkupToMarkdown.convert(text);
		HtmlFreeOutput out = new HtmlFreeOutput();
		out.setFreeOutput(html);
		
		div.addChildren(out);
		return div;
	}

	protected UIComponent getReplyActionsComponent(PostTO post,
			boolean adminMode, boolean topicLocked) {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(JWLStyleClass.FORUM_REPLY_ACTIONS);

		if (!topicLocked&&hasPostReplyPermission()){
			div.addChildren(getReplyLinkComponent());
			div.addChildren(getQuoteLinkComponent(post.getId()));
		}
		if (adminMode&&hasDeletePostPermission()){
			div.addChildren(getAdministrationForm(post.getId()));
		}
		return div;
	}

	protected HtmlLink getArticleLinkComponent(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, title);
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		
		HtmlLink link = this.getHtmlLink("Back to article", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}

	protected HtmlLink getTopicListLink(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_LIST.id);
		params.put(JWLURLParams.ARTICLE_TITLE, title);

		HtmlLink link = getHtmlLink("Topic list", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}

	protected HtmlLink getReplyLinkComponent() {
		Map<String, String> params = new HashMap<String, String>();
		// TODO set again all jwl parameters that have been set in former request
		params.put(JWLURLParams.ANSWERING, "yes");
		
		HtmlLink link = this.getHtmlLink("Reply", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}

	protected HtmlLink getQuoteLinkComponent(Integer postId) {
		Map<String, String> params = new HashMap<String, String>();
		// TODO set again all jwl parameters that have been set in former request
		params.put(JWLURLParams.ANSWERING, "yes");
		params.put(JWLURLParams.QUOTE_POST_ID, postId.toString());
		
		HtmlLink link = this.getHtmlLink("Quote", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}

	private UIComponent getImageComponent(String imgSrc, int imgWidth, int imgHeight) {
		HtmlGraphicImage image = new HtmlGraphicImage(); 
		image.setWidth(String.valueOf(imgWidth));
		image.setHeight(String.valueOf(imgHeight));
		image.setValue(imgSrc);
		return image;
	}

	protected UIComponent getDeleteButton() {
		return super.getHtmlSubmitComponent(JWLElements.FORM_DELETE, 
				JWLStyleClass.FORUM_REPLIES_DELETE);
	}

	protected HtmlActionForm encodedReplyForm(TopicTO topic, PostTO quotedPost) {
		Map<String, String> params = new HashMap<String, String>();
		params.remove(JWLURLParams.ANSWERING);
		params.remove(JWLURLParams.QUOTE_POST_ID);
		params.put(JWLURLParams.DO, "postReplyRequest");
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_POST_REPLY_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));
		
		List<UIComponent> formData = form.getChildren();
		formData.add(getReplyFormPanel(topic, quotedPost));
		formData.add(getReplyFormActionButtons(topic));
		
		return form;
	}

	protected UIComponent getReplyButton() {
		return super.getHtmlSubmitComponent(JWLElements.FORUM_POST_REPLY, 
				JWLStyleClass.ACTION_BUTTON_SMALLER);
	}

	protected UIComponent getReplyCancelButton() {
		return super.getHtmlSubmitComponent(JWLElements.FORUM_POST_CANCEL, 
				null);
	}

	protected UIComponent getHiddenTopicId(Integer topicId) {
		HtmlInputHidden hiddenTopicId = new HtmlInputHidden();
		hiddenTopicId.setId(JWLElements.FORUM_POST_TOPIC_ID.id);
		hiddenTopicId.setValue(topicId.toString());
		return hiddenTopicId;
	}

	protected UIComponent getReplyText(String initText) {
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		textArea.setRows(15);
		textArea.setCols(40);
		textArea.setValue(initText);
		textArea.setId(JWLElements.FORUM_POST_TEXT.id);
		return textArea;
	}

	protected UIComponent getReplyFormPanel(TopicTO topic, PostTO quotedPost) {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		
		HtmlOutputText text = getHtmlText("Post reply");
		text.setStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		panelBody.addChildren(getReplyText(getInitText(quotedPost)));
		panelBody.addChildren(getHiddenTopicId(topic.getId()));
		
		panel.addChildren(text);
		panel.addChildren(panelBody);
		return panel;
	}

	protected UIComponent getReplyFormActionButtons(TopicTO topic) {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		buttonsPanel.addChildren(getReplyButton());
		buttonsPanel.addChildren(getReplyCancelButton());
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
	

}
