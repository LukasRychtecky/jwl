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
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLActions;
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
import com.jwl.presentation.url.Linker;

public class EncodeTopicView extends AbstractEncoder {

	protected ArticleTO article;
	private TopicTO topic;
	protected Boolean answering;
	protected Integer quotePostId;

	private final String png_lock = "/jwl/img/lock.png";
	
	public EncodeTopicView(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
		this.topic = (TopicTO) params.get("topic");
		this.answering = (Boolean) params.get("answering");
		this.quotePostId = (Integer) params.get("quotePostId");
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodedTopic(topic));
		components.add(this.encodedActionButtons(topic, article));
		
		List<PostTO> replies = topic.getPosts();
		PostTO initialPost = replies.get(0);
		replies.remove(0);
		components.add(this.encodedReply(replies, topic.isClosed()));
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
		panel.addStyleClass(JWLStyleClass.PANEL);
		
		HtmlDiv title = new HtmlDiv();
		title.setText("Article topic");
		title.addStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlDiv body = new HtmlDiv();
		body.addStyleClass(JWLStyleClass.PANEL_BODY);
		body.addChildren(getInitialPostComponent(topic.getOpeningPost(), topic));
		
		panel.addChildren(title);
		panel.addChildren(body);
		return panel;
	}


	protected UIComponent getInitialPostComponent(PostTO post, TopicTO topic)  {
		HtmlDiv component = new HtmlDiv();
		component.addStyleClass(JWLStyleClass.FORUM_INITIAL_POST);
		component.addChildren(getPostHeader(topic, post));
		component.addChildren(getPostTextComponet(post.getText()));
		return component;
	}

	
	protected HtmlDiv encodedActionButtons(TopicTO topic, ArticleTO article) {
		HtmlDiv actionButtons = new HtmlDiv();
		actionButtons.addStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		
		actionButtons.addChildren(getArticleLinkComponent(article.getTitle()));
		actionButtons.addChildren(getTopicListLink(article.getTitle()));
		
		if (!topic.isClosed() && hasPostReplyPermission()) {
			actionButtons.addChildren(getReplyLinkComponent());
			actionButtons.addChildren(getQuoteLinkComponent(topic.getOpeningPost().getId()));
		}
		return actionButtons;
	}

	protected HtmlDiv encodedReply(List<PostTO> replies, boolean topicLocked) {
		HtmlDiv panel = new HtmlDiv();
		panel.addStyleClass(JWLStyleClass.PANEL);
		
		HtmlDiv title = new HtmlDiv();
		title.setText(replies.size() + " replies");
		title.addStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlDiv body = new HtmlDiv();
		body.addStyleClass(JWLStyleClass.PANEL_BODY);
		body.getChildren().add(getResponses(replies, topicLocked));
		
		panel.addChildren(title);
		panel.addChildren(body);
		return panel;
	}

	protected HtmlActionForm getAdministrationForm(Integer postId) {
		// TODO PJ Doesn't we should set some parameters to form action
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_VIEW.id);
		params.put(JWLURLParams.TOPIC_ID, String.valueOf(topic.getId()));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		params.put(JWLURLParams.DO, JWLActions.FORUM_POST_DELETE.id);
		
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_POST_ADMIN_FORM.id + "-" + postId.toString());
		form.setStyleClass(JWLStyleClass.FORUM_ACTIONS_FORM);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));
		form.getChildren().add(getDeleteButton());
		return form;
	}

	protected UIComponent getResponses(List<PostTO> replies, boolean topicLocked) {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_REPLIES);
		table.setColumns(1);
		List<UIComponent> tableData = table.getChildren();
		for (PostTO reply : replies) {
			tableData.add(getReplyComponent(reply, topicLocked));
		}
		return table;
	}

	protected UIComponent getReplyComponent(PostTO post,
			boolean topicLocked) {
		HtmlDiv component = new HtmlDiv();
		component.addStyleClass(JWLStyleClass.FORUM_INITIAL_POST);
		component.addChildren(getReplyHeader(post.getAuthor(), post.getCreated()));
		component.addChildren(getPostTextComponet(post.getText()));
		component.addChildren(getReplyActionsComponent(post, topicLocked));
		return component;
	}

	protected UIComponent getPostHeader(TopicTO topic, PostTO post)  {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_POST_HEADER);
		table.setColumns(2);
		table.setColumnClasses(
				JWLStyleClass.FORUM_POST_HEADER_TITLE + "," +
				JWLStyleClass.FORUM_POST_AUTHOR);
		
		HtmlDiv titleDiv = new HtmlDiv();
		HtmlOutputText title = getHtmlText(topic.getTitle());
		titleDiv.addChildren(title);
		if (topic.isClosed()) {
			titleDiv.addChildren(getImageComponent(png_lock, 16, 16));
		}
		
		HtmlDiv authorDiv = new HtmlDiv();
		authorDiv.setText(post.getAuthor());
		HtmlDiv dateDiv = new HtmlDiv();
		dateDiv.setText(post.getCreated().toString());
		
		HtmlFreeOutput headerData = new HtmlFreeOutput();
		headerData.getChildren().add(dateDiv);
		headerData.getChildren().add(authorDiv);
		
		table.getChildren().add(titleDiv);
		table.getChildren().add(headerData);
		return table;
	}

	protected UIComponent getReplyHeader(String author, Date date) {
		HtmlDiv component = new HtmlDiv();
		component.addStyleClass(JWLStyleClass.FORUM_POST_AUTHOR);
		
		HtmlDiv authorDiv = new HtmlDiv();
		authorDiv.setText(author);
		HtmlDiv dateDiv = new HtmlDiv();
		dateDiv.setText(date.toString());
		
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

	protected UIComponent getReplyActionsComponent(PostTO post, boolean topicLocked) {
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.FORUM_REPLY_ACTIONS);

		if (!topicLocked&&hasPostReplyPermission()){
			div.addChildren(getReplyLinkComponent());
			div.addChildren(getQuoteLinkComponent(post.getId()));
		}
		if (hasDeletePostPermission()){
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
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_VIEW.id);
//		params.put(JWLURLParameters.QUOTE_POST_ID, ArticleActions.FORUM_TOPIC_VIEW);
		params.put(JWLURLParams.TOPIC_ID,  String.valueOf(topic.getId()));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		
		HtmlLink link = this.getHtmlLink("Reply", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}

	protected HtmlLink getQuoteLinkComponent(Integer postId) {
		Map<String, String> params = new HashMap<String, String>();
		// TODO set again all jwl parameters that have been set in former request
		params.put(JWLURLParams.ANSWERING, "yes");
		params.put(JWLURLParams.QUOTE_POST_ID, postId.toString());
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_VIEW.id);
		params.put(JWLURLParams.TOPIC_ID,  String.valueOf(topic.getId()));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		
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
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_VIEW.id);
		params.put(JWLURLParams.TOPIC_ID, String.valueOf(topic.getId()));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		params.put(JWLURLParams.DO, JWLActions.FORUM_POST_REPLY.id);
		
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
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_VIEW.id);
		params.put(JWLURLParams.TOPIC_ID, String.valueOf(topic.getId()));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());

		HtmlLink link = getHtmlLink(JWLElements.FORUM_TOPIC_CANCEL.value, params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
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
		textArea.setStyleClass(JWLStyleClass.MARK_ME);
		return textArea;
	}

	protected UIComponent getReplyFormPanel(TopicTO topic, PostTO quotedPost) {
		HtmlDiv panel = new HtmlDiv();
		panel.addStyleClass(JWLStyleClass.PANEL);
		
		HtmlDiv head = new HtmlDiv();
		head.setText("Post reply");
		head.addStyleClass(JWLStyleClass.PANEL_HEADER);
		
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.addStyleClass(JWLStyleClass.PANEL_BODY);
		panelBody.addChildren(getReplyText(getInitText(quotedPost)));
		panelBody.addChildren(getHiddenTopicId(topic.getId()));
		
		panel.addChildren(head);
		panel.addChildren(panelBody);
		return panel;
	}

	protected UIComponent getReplyFormActionButtons(TopicTO topic) {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.addStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
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
