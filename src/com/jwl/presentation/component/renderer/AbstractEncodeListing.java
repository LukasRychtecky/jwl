package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlFreeOutput;
import com.jwl.util.html.component.HtmlHeaderPanelGrid;
import com.jwl.util.html.component.HtmlLinkProperties;
import com.jwl.util.html.url.URLBuilder;

public abstract class AbstractEncodeListing extends JWLEncoder {

	private final String[] headers = new String[]{"Title", "Tags", "Editor",
"Editing count", "Created", "Rating", "Actions" };

	protected List<String> getHeaderNames() {
		return Arrays.asList(this.headers);
	}

	protected void encodeLinkToCreateNewArticle() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Create new article");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.EDIT);
		properties.addClass(JWLStyleClass.CREATE_NEW_ARTICLE);
		properties.addClass(JWLStyleClass.ACTION_BUTTON);

		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		link.encodeAll(this.context);
	}

	protected void encodeListing(IPaginator<ArticleTO> paginator, List<String> headers,
			Map<Integer, String> orderableColumns) throws IOException {
		List<String> orderableHeaders = this.encodeHeaders(headers,
				orderableColumns);
		HtmlPanelGrid table = getTable(orderableHeaders);
		List<UIComponent> articlesTableData = table.getChildren();
		List<ArticleTO> articles = paginator.getCurrentPageContent();
		for (ArticleTO article : articles) {
			if (this.hasViewPermission(article.getId())) {
				this.encodeRowData(article, articlesTableData);
			}
		}
		table.encodeAll(this.context);

		HtmlPanelGroup pagingContainer = new HtmlPanelGroup();
		pagingContainer.setStyleClass("jwl-paging");
		List<UIComponent> children = pagingContainer.getChildren();

		children.add(this.createLinkToFirstPage(paginator));
		if (paginator.hasPrevious()) {
			children.add(this.createLinkToPreviousPage(paginator));
		}
		if (paginator.hasNext()) {
			children.add(this.createLinkToNextPage(paginator));
		}
		children.add(this.createLinkToLastPage(paginator));

		pagingContainer.encodeAll(this.context);
	}

	protected void encodeListing(List<ArticleTO> articles, List<String> headers)
			throws IOException {
		HtmlPanelGrid table = getTable(headers);
		List<UIComponent> articlesTableData = table.getChildren();
		for (ArticleTO article : articles) {
			if (this.hasViewPermission(article.getId())) {
				this.encodeRowData(article, articlesTableData);
			}
		}
		table.encodeAll(this.context);
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

	protected void encodeRowData(ArticleTO article,
			List<UIComponent> articlesTableData) {

		articlesTableData.add(this.getArticleLinkComponent(article.getTitle()));

		StringBuilder tags = new StringBuilder();
		for (String tag : article.getTags()) {
			tags.append(tag).append(", ");
		}

		String separatedTags = "";
		if (tags.length() > 0) {
			separatedTags = tags.substring(0, tags.length() - 2);
		}

		articlesTableData.add(this.getTagsComponent(separatedTags));
		articlesTableData.add(this.getEditorComponent(article.getEditor()));
		articlesTableData.add(this.getEditingCountComponent(article.getEditCount()));
		articlesTableData.add(this.getCreatedComponent(article.getCreated().toString()));
		articlesTableData.add(this.getRatingComponent(article
				.getRatingAverage()));
		encodeAdditionalRowData(article, articlesTableData);
	}

	protected List<UIComponent> encodeAdditionalRowData(ArticleTO article,
			List<UIComponent> articlesTableData) {
		HtmlPanelGroup group = new HtmlPanelGroup();
		group.setLayout("block");
		List<UIComponent> actions = group.getChildren();
		// TODO: check permission

		actions.add(this.encodeActionLink(article.getId().getId(), "Restore",
				ArticleActions.HISTORY_LIST, JWLStyleClass.ACTION_LINK));

		articlesTableData.add(group);
		return actions;
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

	protected UIComponent getArticleLinkComponent(String title) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(title);
		properties.addParameter(JWLURLParameters.ARTICLE_TITLE, title);
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.VIEW);
		// properties.addClass(JWLStyleClass.ACTION_BUTTON_STYLE);
		return this.getHtmlLinkComponent(properties);
	}

	private UIComponent getTagsComponent(String tagsString) {
		return this.getHtmlTextComponent(tagsString);
	}

	private UIComponent getEditorComponent(String editorValue) {
		return this.getHtmlTextComponent(editorValue);
	}

	private UIComponent getEditingCountComponent(int editingCountValue) {
		return this.getHtmlTextComponent(editingCountValue);
	}

	private UIComponent getCreatedComponent(String createdValue) {
		return this.getHtmlTextComponent(createdValue);
	}

	private boolean hasViewPermission(ArticleId id) {
		return this.hasPermission(com.jwl.business.security.AccessPermissions.ARTICLE_VIEW, id);
	}

	private UIComponent createLinkToNextPage(IPaginator<ArticleTO> paginator) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(">");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addParameter(JWLURLParameters.LIST_PAGE_NUMBER,
				paginator.getNextPageIndex());

		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.LINK_NEXT_PAGE);
		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		return link;
	}

	private UIComponent createLinkToPreviousPage(IPaginator<ArticleTO> paginator)
			throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("<");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addParameter(JWLURLParameters.LIST_PAGE_NUMBER,
				paginator.getPreviousPageIndex());
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.LINK_PREVIOUS_PAGE);
		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		return link;
	}

	private UIComponent createLinkToFirstPage(IPaginator<ArticleTO> paginator) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("<<");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addParameter(JWLURLParameters.LIST_PAGE_NUMBER,
				paginator.getFirstPageIndex());

		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.LINK_FIRST_PAGE);
		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		return link;
	}

	private UIComponent createLinkToLastPage(IPaginator<ArticleTO> paginator) throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(">>");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addParameter(JWLURLParameters.LIST_PAGE_NUMBER,
				paginator.getLastPageIndex());
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.LINK_LAST_PAGE);
		HtmlOutputLink link = this.getHtmlLinkComponent(properties);
		return link;
	}

	private List<String> encodeHeaders(List<String> headers,
			Map<Integer, String> orderableColumns) {
		if (orderableColumns == null) {
			return headers;
		}
		List<String> result = new ArrayList<String>();
		int i = 1;
		for (String header : headers) {
			String oc = orderableColumns.get(i);
			if (oc == null) {
				result.add(header);
			} else {
				result.add(this.getOrderColumnLink(header, oc));
			}
			i++;
		}
		return result;
	}

	private String getOrderColumnLink(String header, String columnName) {
		WikiURLParser parser = new WikiURLParser();
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addParameter(JWLURLParameters.LIST_ORDER_COLUMN, columnName);
		properties.setHref(parser.getCurrentURL());
		properties.addParameters(parser.getURLParametersMinusArticleParameters());
		String url = properties.getHref();
		Map<String, String> attributes = properties.getParameters();
		String link = URLBuilder.buildURL(url, attributes);
		link = link.replaceAll("&", "&amp;");
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"").append(link).append("\">");
		sb.append(header);
		sb.append("</a>");
		return sb.toString();
	}

	private UIComponent getRatingComponent(float rating) {
		int sn = (int) rating;
		int r = (int) (rating %1) * 10;
		if (r >= 5) {
			sn++;
		}
		HtmlFreeOutput output = new HtmlFreeOutput();
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"smallstars\">");
		for (int i = 0; i < 10; i++) {
			sb.append("<div class=\"");
			boolean g = false;
			if (i < sn) {
				sb.append("rating");
				g = true;
			}
			if (i % 2 == 1) {
				if (g) {
					sb.append(" ");
				}
				sb.append("rating-right");
			}
			sb.append("\">");
			sb.append("</div>");
		}
		sb.append("</div>");
		output.setFreeOutput(sb.toString());
		return output;
	}
}
