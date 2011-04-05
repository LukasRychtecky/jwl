package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.IFacade;
import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.security.AccessPermissions;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDiv;
import com.jwl.util.html.component.HtmlFreeOutput;
import com.jwl.util.html.component.HtmlHeaderPanelGrid;
import com.jwl.util.html.component.HtmlLinkProperties;
import com.jwl.util.html.url.URLBuilder;

public abstract class AbstractEncodeTopicList extends JWLEncoder {

	protected ArticleId articleId;
	protected final String[] headers = new String[] { "", "Topic", "Author",
			"Created", "Replies" };

	public AbstractEncodeTopicList(IFacade facade, ArticleId articleId) {
		super(facade);
		this.articleId = articleId;
	}

	protected List<String> getHeaderNames() {
		return Arrays.asList(this.headers);
	}

	protected void encodeForm(IPaginator<TopicTO> paginator,
			List<String> headers) throws IOException {
		paginator.setUpPaginator();
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.getFormAction());
		List<UIComponent> formData = form.getChildren();
		encodePanel(formData, paginator, headers);
		encodePanelActions(formData);
		form.encodeAll(context);
	}

	protected void encodePanel(List<UIComponent> formData,
			IPaginator<TopicTO> paginator, List<String> headers)
			throws IOException {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Article topics");
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		HtmlPanelGrid table = encodeListing(paginator, headers);
		panelBody.getChildren().add(table);
		panelBody.getChildren().add(getPageButtonsComponent(paginator));
		List<UIComponent> panelChildern = panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		formData.add(panel);
	}

	protected void encodePanelActions(List<UIComponent> formData) {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(this.getArticleLinkComponent(articleId));
		if(hasCreateTopicPermission()){
			panelChildren.add(this.getCreateTopicLinkComponent(articleId));
		}
		formData.add(buttonsPanel);
	}

	protected HtmlPanelGrid encodeListing(IPaginator<TopicTO> paginator,
			List<String> headers) throws IOException {
		HtmlPanelGrid table = getTable(headers);
		List<UIComponent> articlesTableData = table.getChildren();
		List<TopicTO> topics = paginator.getCurrentPageContent();
		for (TopicTO topic : topics) {
			this.encodeRowData(topic, articlesTableData);
		}
		return table;
	}

	private HtmlPanelGrid getTable(List<String> headers) {
		HtmlHeaderPanelGrid table = new HtmlHeaderPanelGrid();
		table.setColumns(headers.size());
		table.setCellpadding("0");
		table.setCellspacing("0");
		table.setHeaders(headers);
		table.setStyleClass(JWLStyleClass.TABLE_OF_ARTICLES);
		table.setHeaderClass(JWLStyleClass.TABLE_HEADER_OF_ARTICLES);
		return table;
	}

	protected void encodeRowData(TopicTO topic, List<UIComponent> topicTableData)
			throws IOException {
		topicTableData.add(this.getServiceColumn(topic));
		topicTableData.add(this.getTitleComponent(topic.getTitle(), topic.getId()));
		topicTableData.add(this.getAuthorComponent(topic.getOpeningPost()
				.getAuthor()));
		topicTableData.add(this.getCreatedComponent(topic.getOpeningPost()
				.getCreated().toString()));
		topicTableData.add(this.getRepliesCountComponent(topic.getPosts()
				.size() - 1));
		//topicTableData.add(this.getLastPostComponent(topic.getLastReply()));
	}

	protected UIComponent encodeActionLink(int articleId, String title,
			String action) {
		return encodeActionLink(articleId, title, action, "");
	}

	protected UIComponent encodeActionLink(int articleId, String title,
			String action, String styleClass) {

		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(title);
		properties.addParameter(JWLURLParameters.ACTION, action);
		properties.addParameter(JWLURLParameters.ARTICLE_ID, articleId);
		properties.addClass(styleClass);
		return this.getHtmlLinkComponent(properties);
	}

	protected UIComponent getArticleLinkComponent(ArticleId articleId) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to article");
		properties.addParameter(JWLURLParameters.ARTICLE_ID, articleId.getId());
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.VIEW);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return this.getHtmlLinkComponent(properties);
	}

	protected UIComponent getCreateTopicLinkComponent(ArticleId articleId) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Create topic");
		properties.addParameter(JWLURLParameters.ARTICLE_ID, articleId.getId());
		properties.addParameter(JWLURLParameters.ACTION,
				ArticleActions.FORUM_TOPIC_CREATE);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return this.getHtmlLinkComponent(properties);
	}

	protected UIComponent getServiceColumn(TopicTO topic) throws IOException {		
		if (topic.isClosed()) {
			return getImage("/SeamWiki/jwl/lock.png", 16, 16);
		} else {
			HtmlFreeOutput out = new HtmlFreeOutput();
			out.setFreeOutput(" ");
			return out;
		}	
	}

	private UIComponent getTitleComponent(String title, Integer topicId) {
		return this.getTopicLink(topicId, title);
	}

	private UIComponent getAuthorComponent(String editorValue) {
		return this.getHtmlTextComponent(editorValue);
	}

	private UIComponent getRepliesCountComponent(int repliesCountValue) {
		return this.getHtmlTextComponent(repliesCountValue);
	}

	private UIComponent getCreatedComponent(String createdValue) {
		return this.getHtmlTextComponent(createdValue);
	}

	private UIComponent getLastPostComponent(PostTO lastPost) throws IOException {
		if (lastPost != null) {
			HtmlFreeOutput out = new HtmlFreeOutput();
			HtmlDiv createdDiv = new HtmlDiv();
			createdDiv.setValue(lastPost.getCreated());
			HtmlDiv authorDiv = new HtmlDiv();
			authorDiv.setValue(lastPost.getAuthor());
			List<UIComponent> outChildren = out.getChildren();
			outChildren.add(createdDiv);
			outChildren.add(authorDiv);
			outChildren.add(getImageLink("", "/SeamWiki/jwl/page_white_go.png", 16, 16));
			return out;
		}
		return this.getHtmlTextComponent("-");
	}

	private boolean hasCreateTopicPermission() {
		return this.hasPermission(AccessPermissions.FORUM_CREATE_TOPIC);
	}

	private UIComponent getPageButtonsComponent(IPaginator<TopicTO> paginator)
			throws IOException {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_PAGE_BUTTONS_TABLE);
		table.setColumns(5);
		table.setColumnClasses(JWLStyleClass.FORUM_PAGE_BUTTON+","+JWLStyleClass.FORUM_PAGE_BUTTON+","+JWLStyleClass.FORUM_PAGE_TEXT+","+JWLStyleClass.FORUM_PAGE_BUTTON+","+JWLStyleClass.FORUM_PAGE_BUTTON);
		List<UIComponent> tableData = table.getChildren();
		tableData.add(getLinkToFirstPage(paginator));
		tableData.add(getLinkToPreviousPage(paginator));
		tableData.add(getPageText(paginator));
		tableData.add(getLinkToNextPage(paginator));
		tableData.add(getLinkToLastPage(paginator));
		return table;
	}

	private UIComponent getLinkToNextPage(IPaginator<TopicTO> paginator)
			throws IOException {
		if (!paginator.hasNext()) {
			return getImage("/SeamWiki/jwl/blank.gif", 13, 11);
		}

		WikiURLParser parser = new WikiURLParser();
		String articleId = parser.getArticleId();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.putAll(parser.getURLParametersMinusArticleParameters());
		attributes
				.put(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_LIST);
		attributes.put(JWLURLParameters.LIST_PAGE_NUMBER,
				new Integer(paginator.getNextPageIndex()).toString());
		attributes.put(JWLURLParameters.ARTICLE_ID, articleId);
		String link = URLBuilder.buildURL(parser.getCurrentURL(), attributes);
		return getImageLink(link, "/SeamWiki/jwl/page.next.gif", 13, 11);
	}

	private UIComponent getLinkToPreviousPage(IPaginator<TopicTO> paginator)
			throws IOException {
		if (!paginator.hasPrevious()) {
			return getImage("/SeamWiki/jwl/blank.gif", 13, 11);
		}
		WikiURLParser parser = new WikiURLParser();
		String articleId = parser.getArticleId();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.putAll(parser.getURLParametersMinusArticleParameters());
		attributes
				.put(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_LIST);
		attributes.put(JWLURLParameters.LIST_PAGE_NUMBER,
				new Integer(paginator.getPreviousPageIndex()).toString());
		attributes.put(JWLURLParameters.ARTICLE_ID, articleId);
		String link = URLBuilder.buildURL(parser.getCurrentURL(), attributes);
		return getImageLink(link, "/SeamWiki/jwl/page.previous.gif", 13, 11);
	}

	private UIComponent getLinkToFirstPage(IPaginator<TopicTO> paginator)
			throws IOException {
		if (!paginator.hasPrevious()) {
			return getImage("/SeamWiki/jwl/blank.gif", 13, 11);
		}
		WikiURLParser parser = new WikiURLParser();
		String articleId = parser.getArticleId();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.putAll(parser.getURLParametersMinusArticleParameters());
		attributes
				.put(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_LIST);
		attributes.put(JWLURLParameters.LIST_PAGE_NUMBER,
				new Integer(paginator.getFirstPageIndex()).toString());
		attributes.put(JWLURLParameters.ARTICLE_ID, articleId);

		String link = URLBuilder.buildURL(parser.getCurrentURL(), attributes);
		return getImageLink(link, "/SeamWiki/jwl/page.first.gif", 13, 11);
	}

	private UIComponent getLinkToLastPage(IPaginator<TopicTO> paginator)
			throws IOException {
		if (!paginator.hasNext()) {
			return getImage("/SeamWiki/jwl/blank.gif", 13, 11);
		}
		WikiURLParser parser = new WikiURLParser();
		String articleId = parser.getArticleId();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.putAll(parser.getURLParametersMinusArticleParameters());
		attributes
				.put(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_LIST);
		attributes.put(JWLURLParameters.LIST_PAGE_NUMBER,
				new Integer(paginator.getLastPageIndex()).toString());
		attributes.put(JWLURLParameters.ARTICLE_ID, articleId);

		String link = URLBuilder.buildURL(parser.getCurrentURL(), attributes);
		return getImageLink(link, "/SeamWiki/jwl/page.last.gif", 13, 11);
	}

	private UIComponent getPageText(IPaginator<TopicTO> paginator) {
		StringBuilder sb = new StringBuilder();
		sb.append(paginator.getCurrentPageFirst());
		sb.append(" - ");
		sb.append(paginator.getCurrentPageLast());
		sb.append(" out of ");
		sb.append(paginator.getContentSize());
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(sb.toString());
		return text;
	}

	private UIComponent getImageLink(String url, String imgSrc, int imgWidth,
			int imgHeight) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"" + url + "\" >");
		sb.append("<img width =\"" + imgWidth + "\" height =\"" + imgHeight
				+ "\" src=\"" + imgSrc + "\" />");
		sb.append("</a>");
		HtmlFreeOutput component = new HtmlFreeOutput();
		component.setFreeOutput(sb.toString());
		return component;
	}

	private UIComponent getImage(String imgSrc, int imgWidth, int imgHeight)
			throws IOException {
		String image = "<img width =\"" + imgWidth + "\" height =\"" + imgHeight
		+ "\" src=\"" + imgSrc + "\" />";
		HtmlFreeOutput component = new HtmlFreeOutput();
		component.setFreeOutput(image);
		return component;
	}

	@Override
	protected String getFormAction() {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String target = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_LIST);
		params.put(JWLURLParameters.ARTICLE_ID, articleId.getId().toString());
		return getFormActionString(context, target, params);
	}
	
	protected UIComponent getTopicLink(Integer topicId, String title){
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(title);
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_VIEW);
		properties.addParameter(JWLURLParameters.TOPIC_ID, topicId);
		properties.addParameter(JWLURLParameters.ARTICLE_ID, articleId.getId().toString());
		return super.getHtmlLinkComponent(properties);
	}

}
