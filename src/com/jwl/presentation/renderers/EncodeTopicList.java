package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.TopicTO;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLTableHeaders;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlHeaderPanelGrid;
import com.jwl.presentation.html.HtmlLink;

public class EncodeTopicList extends AbstractEncoder {

	private final String gif_blank = "/SeamWiki/jwl/blank.gif";
	private final String gif_page_next = "/SeamWiki/jwl/page.next.gif";
	private final String gif_page_previous = "/SeamWiki/jwl/page.previous.gif";
	private final String gif_page_first = "/SeamWiki/jwl/page.first.gif";
	private final String gif_page_last = "/SeamWiki/jwl/page.last.gif";
	private final String png_lock = "/SeamWiki/jwl/lock.png";
	
	private List<UIComponent> columns;
	protected ArticleTO article;
	private IPaginator<TopicTO> paginator;
	
	public EncodeTopicList(ArticleTO article, IPaginator<TopicTO> paginator) {
		super();
		this.article = article;
		this.paginator = paginator;
		this.paginator.setUpPaginator();
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		// TODO Check that user has forum view permission, article is not locked 
		components.add(this.encodedForm());	
		return components;
	}
	
	private List<UIComponent> getHeaders() {
		if (columns == null) {
			columns = new ArrayList<UIComponent>();
			if (hasDeleteTopicPermission()) {
				columns.add(getFreeOutput(JWLTableHeaders.BLANK.value));
			}
			columns.add(getFreeOutput(JWLTableHeaders.BLANK.value));
			columns.add(getFreeOutput(JWLTableHeaders.TOPIC.value));
			columns.add(getFreeOutput(JWLTableHeaders.EDITOR.value));
			columns.add(getFreeOutput(JWLTableHeaders.CREATED.value));
			columns.add(getFreeOutput(JWLTableHeaders.REPLIES.value));
		}
		return columns;
	}
	
	public HtmlFreeOutput getFreeOutput(String text) {
		HtmlFreeOutput output = new HtmlFreeOutput();
		output.setFreeOutput(text);
		return output;
	} 
	
	private HtmlActionForm encodedForm() {
		Map<String, String> params = this.parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_LIST.id);
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));
		
		form.getChildren().add(encodedPanel());
		form.getChildren().add(encodedPanelActions());
		
		return form;
	}

	private HtmlDiv encodedPanel() {
		
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Article topics");
		
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		panelBody.getChildren().add(encodedListing());
		panelBody.getChildren().add(encodedPageButtons());
		
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		panel.getChildren().add(panelHeader);
		panel.getChildren().add(panelBody);
		
		return panel;
	}

	private HtmlDiv encodedPanelActions() {
		
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		
		if (hasDeleteTopicPermission()) {
			panelChildren.add(getDeleteButton());
		}
		if (hasCloseTopicPermission()) {
			panelChildren.add(getCloseButton());
			panelChildren.add(getOpenButton());
		}
		
		panelChildren.add(this.getArticleLinkComponent());
		
		if(hasCreateTopicPermission()){
			panelChildren.add(this.getCreateTopicLinkComponent());
		}
		
		return buttonsPanel;
	}

	private HtmlPanelGrid encodedListing() {
		HtmlPanelGrid table = getTable();
		List<UIComponent> tableChildren = table.getChildren();
		
		List<TopicTO> topics = paginator.getCurrentPageContent();
		for (TopicTO topic : topics) {
			this.encodeRowData(topic, tableChildren);
		}
		return table;
	}

	private HtmlPanelGrid getTable() {
		HtmlHeaderPanelGrid table = new HtmlHeaderPanelGrid();
		table.setColumns(getHeaders().size());
		table.setCellpadding("0");
		table.setCellspacing("0");
		table.setHeaders(getHeaders());
		table.setStyleClass(JWLStyleClass.TABLE_OF_ARTICLES);
		table.setHeaderClass(JWLStyleClass.TABLE_HEADER_OF_ARTICLES);
		return table;
	}

	private void encodeRowData(TopicTO topic, List<UIComponent> topicTable) {
		if (hasDeleteTopicPermission()) {
			HtmlSelectBooleanCheckbox chbx = new HtmlSelectBooleanCheckbox();
			chbx.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + JWLStyleClass.HTML_ID_SEPARATOR
					+ JWLElements.FORUM_TOPIC_CHBX.id + JWLStyleClass.HTML_ID_SEPARATOR + topic.getId());
			topicTable.add(chbx);
		}
		topicTable.add(this.getServiceColumn(topic));
		topicTable.add(this.getTitleComponent(topic.getId(), topic.getTitle()));
		topicTable.add(this.getAuthorComponent(topic.getOpeningPost().getAuthor()));
		topicTable.add(this.getCreatedComponent(topic.getOpeningPost().getCreated())); 
		topicTable.add(this.getRepliesCountComponent(topic.getPosts().size() - 1));		
	}

	private UIComponent getServiceColumn(TopicTO topic) {		
		if (topic.isClosed()) {
			return getImage(png_lock , 16, 16);
		} else {
			HtmlFreeOutput out = new HtmlFreeOutput();
			out.setFreeOutput(" ");
			return out;
		}
	}
	
	private HtmlLink getArticleLinkComponent() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		HtmlLink link = this.getHtmlLink("Back to article", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}

	private HtmlLink getCreateTopicLinkComponent() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_CREATE.id);
		HtmlLink link = this.getHtmlLink("Create topic", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		return link;
	}
	
	protected UIComponent getDeleteButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + JWLStyleClass.HTML_ID_SEPARATOR
				+ JWLElements.FORUM_TOPIC_DELETE.id);
		button.setValue(JWLElements.FORUM_TOPIC_DELETE.value);
		button.setType("submit");
		return button;
	}

	protected UIComponent getCloseButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + JWLStyleClass.HTML_ID_SEPARATOR
				+ JWLElements.FORUM_TOPIC_CLOSE.id);
		button.setValue(JWLElements.FORUM_TOPIC_CLOSE.value);
		button.setType("submit");
		return button;
	}

	protected UIComponent getOpenButton() {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(JWLElements.FORUM_TOPIC_ADMIN_FORM.id + JWLStyleClass.HTML_ID_SEPARATOR
				+ JWLElements.FORUM_TOPIC_OPEN.id);
		button.setValue(JWLElements.FORUM_TOPIC_OPEN.value);
		button.setType("submit");
		return button;
	}

	private UIComponent getTitleComponent(int topicId, String topicTitle) {
		return this.getTopicLink(topicId, topicTitle);
	}

	private UIComponent getAuthorComponent(String editorValue) {
		return this.getHtmlText(editorValue);
	}

	private UIComponent getRepliesCountComponent(int repliesCountValue) {
		return this.getHtmlText(repliesCountValue);
	}

	private UIComponent getCreatedComponent(Date created) {
		return this.getHtmlText(created.toString());
	}

	private UIComponent encodedPageButtons() {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setStyleClass(JWLStyleClass.FORUM_PAGE_BUTTONS_TABLE);
		table.setColumns(5);
		table.setColumnClasses(
				JWLStyleClass.FORUM_PAGE_BUTTON+","
				+JWLStyleClass.FORUM_PAGE_BUTTON+","
				+JWLStyleClass.FORUM_PAGE_TEXT+","
				+JWLStyleClass.FORUM_PAGE_BUTTON+","
				+JWLStyleClass.FORUM_PAGE_BUTTON);
		List<UIComponent> tableData = table.getChildren();
		tableData.add(getLinkToFirstPage());
		tableData.add(getLinkToPreviousPage());
		tableData.add(getPageText());
		tableData.add(getLinkToNextPage());
		tableData.add(getLinkToLastPage());
		return table;
	}

	private UIComponent getPageText() {
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
	
	private UIComponent getLinkToFirstPage() {
		return getLink(paginator.hasPrevious(), paginator.getFirstPageIndex(),
				gif_page_first);
	}
	
	private UIComponent getLinkToPreviousPage() {
		return getLink(paginator.hasPrevious(),  paginator.getPreviousPageIndex(), 
				gif_page_previous);
	}

	private UIComponent getLinkToNextPage() {
		return getLink(paginator.hasNext(), paginator.getNextPageIndex(), 
				gif_page_next);
	}

	private UIComponent getLinkToLastPage() {
		return getLink(paginator.hasNext(), paginator.getLastPageIndex(), 
				gif_page_last);
	}
	
	private UIComponent getLink(boolean viewCondition, int pageNumber, 
			String gifPath) {
		
		if (!viewCondition) {
			return getImage(gif_blank, 13, 11);
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_LIST.id);
		params.put(JWLURLParams.LIST_PAGE_NUMBER, String.valueOf(pageNumber));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		
		HtmlLink link = getHtmlLink("", params);
		link.getChildren().add(getImage(gifPath, 13, 11));
		return link;
	}

	private HtmlGraphicImage getImage(String imgSrc, int imgWidth, int imgHeight) {
		HtmlGraphicImage image = new HtmlGraphicImage(); 
		image.setWidth(String.valueOf(imgWidth));
		image.setHeight(String.valueOf(imgHeight));
		image.setValue(imgSrc);
		return image;
	}
	
	private HtmlLink getTopicLink(int topicId, String topicTitle){
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_VIEW.id);
//		params.put(JWLURLParameters.QUOTE_POST_ID, ArticleActions.FORUM_TOPIC_VIEW);
		params.put(JWLURLParams.TOPIC_ID, String.valueOf(topicId));
		params.put(JWLURLParams.ARTICLE_TITLE, this.article.getTitle());
		return super.getHtmlLink(topicTitle, params);
	}
}