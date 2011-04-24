package com.jwl.presentation.renderers;

// <editor-fold defaultstate="collapsed">
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLPresenters;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLTableHeaders;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlHeaderPanelGrid;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.renderers.units.RatingComponent;
// </editor-fold>

public class EncodeListing extends AbstractEncoder {

	private List<JWLTableHeaders> columns;
	private List<JWLTableHeaders> orderableColumns;
	protected IPaginator<ArticleTO> paginator;
	
	public EncodeListing(IPaginator<ArticleTO> paginator) {
		super();
		this.paginator = paginator;
	}
	
	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		if (this.hasAdministrationPermission()) {
			components.add(this.encodedLinkToAdministrationConsole());
		}
		components.add(this.encodedLinkToSearch());
		components.add(this.encodedLinkToCreateNewArticle());
		
		components.add(this.encodedListing());
		components.add(this.encodedPaginator());
		
		return components;
	}

	private HtmlLink encodedLinkToAdministrationConsole() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ADMINISTRATION_CONSOLE.id);
		params.put(JWLURLParams.PRESENTER, JWLPresenters.ADMINISTRATION.id);
		
		HtmlLink link = this.getHtmlLink("Administration Console", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}
	
	private HtmlLink encodedLinkToSearch() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(JWLURLParams.STATE, JWLStates.SEARCH.id);
		params.put(JWLURLParams.PRESENTER, JWLPresenters.SEARCH.id);
		
		HtmlLink link = this.getHtmlLink("Search", params);
		link.setStyleClass(JWLStyleClass.ACTION_BUTTON);
		return link;
	}
	
	private HtmlLink encodedLinkToCreateNewArticle() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_CREATE.id);

		HtmlLink link = this.getHtmlLink("Create new article", params);
		link.setStyleClasses(JWLStyleClass.CREATE_NEW_ARTICLE, JWLStyleClass.ACTION_BUTTON);
		link.setIsAjax(Boolean.TRUE);
		return link;
	}

	protected HtmlPanelGrid encodedListing() {
		
		List<UIComponent> headers = this.encodeHeaders();
		HtmlPanelGrid table = getTable(headers);
		
		List<UIComponent> articlesTableData = table.getChildren();	
		
		for (ArticleTO article : paginator.getCurrentPageContent()) {
			articlesTableData.addAll(this.encodeRowData(article));
		}
		return table;
	}
	
	private HtmlPanelGroup encodedPaginator() {
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

		return pagingContainer;
	}

	private HtmlPanelGrid getTable(List<UIComponent> headers) {
		HtmlHeaderPanelGrid table = new HtmlHeaderPanelGrid();
		table.setColumns(headers.size());
		table.setCellpadding("0");
		table.setCellspacing("0");
		table.setHeaders(headers);
		table.setStyleClass(JWLStyleClass.TABLE_OF_ARTICLES);
		table.setHeaderClass(JWLStyleClass.TABLE_HEADER_OF_ARTICLES);
		return table;
	}

	private List<UIComponent> encodeRowData(ArticleTO article) {
		if (!this.hasArticleViewPermission(article.getId())) {
			return Collections.emptyList();
		}

		List<UIComponent> rowDataCells = new ArrayList<UIComponent>(); 
		
		String title = article.getTitle();
		HtmlLink link = this.encodeActionLink(title, title, JWLStates.ARTICLE_VIEW, null);
		link.setIsAjax(Boolean.TRUE);
		rowDataCells.add(link);

		StringBuilder tags = new StringBuilder();
		for (String tag : article.getTags()) {
			tags.append(tag).append(", ");
		}

		String separatedTags = "";
		if (tags.length() > 0) {
			separatedTags = tags.substring(0, tags.length() - 2);
		}

		rowDataCells.add(this.getTagsComponent(separatedTags));
		rowDataCells.add(this.getEditorComponent(article.getEditor()));
		rowDataCells.add(this.getEditingCountComponent(article.getEditCount()));
		rowDataCells.add(this.getCreatedComponent(article.getCreated().toString()));
		rowDataCells.add(this.getRatingComponent(article.getRatingAverage()));
		rowDataCells.add(this.encodeAdditionalRowData(article));
		
		return rowDataCells;
	}

	private HtmlPanelGroup encodeAdditionalRowData(ArticleTO article) {
		ArticleId articleId = article.getId();
		String title = article.getTitle();
		
		HtmlPanelGroup group = new HtmlPanelGroup();
		group.setLayout("block");
		List<UIComponent> actions = group.getChildren();
		
		// TODO Add current paginator page to url
		if (article.isLocked() && hasLockPermission(articleId)) {
			actions.add(this.encodeActionLink(title, "Unlock", JWLStates.ARTICLE_LIST, JWLActions.ARTICLE_UNLOCK));
		}
		
		if (!article.isLocked() && hasLockPermission(articleId)) {
			actions.add(this.encodeActionLink(title, "Lock", JWLStates.ARTICLE_LIST, JWLActions.ARTICLE_LOCK));
		}
		
		if (!article.isLocked() && hasRestorePermission(articleId)) {
			actions.add(this.encodeActionLink(title, "Restore", JWLStates.HISTORY_LIST, null));
		}
		
		if (!article.isLocked() && hasDeletePermission(articleId)) {
			actions.add(this.encodeActionLink(title, "Delete", JWLStates.ARTICLE_LIST, JWLActions.ARTICLE_DELETE));
		}

		return group;
	}

	private HtmlLink encodeActionLink(String title, String text, 
			JWLStates state, JWLActions action) {

		Map<String, String> params = new HashMap<String, String>();
		if (state != null) {
			params.put(JWLURLParams.STATE, state.id);
		}
		if (action != null) {
			params.put(JWLURLParams.DO, action.id);	
		}
		if (title != null) {
			params.put(JWLURLParams.ARTICLE_TITLE, title);
		}
		params.put(JWLURLParams.LIST_PAGE_NUMBER, String.valueOf(paginator.getPageIndex()));
		
		HtmlLink link = this.getHtmlLink(text, params);
		link.setStyleClass(JWLStyleClass.ACTION_LINK);
		return link;
	}
	
	private UIComponent createLinkToFirstPage(IPaginator<ArticleTO> paginator) {
		return getPaginatorLink(paginator.hasPrevious(), "<<",
				paginator.getFirstPageIndex(), JWLStyleClass.LINK_FIRST_PAGE);
	}
	
	private UIComponent createLinkToPreviousPage(IPaginator<ArticleTO> paginator) {
		return getPaginatorLink(paginator.hasPrevious(), "<", 
				paginator.getPreviousPageIndex(), JWLStyleClass.LINK_PREVIOUS_PAGE);
	}

	private UIComponent createLinkToNextPage(IPaginator<ArticleTO> paginator) {
		return getPaginatorLink(paginator.hasNext(), ">", 
				paginator.getNextPageIndex(), JWLStyleClass.LINK_NEXT_PAGE);
	}

	private UIComponent createLinkToLastPage(IPaginator<ArticleTO> paginator) {
		return getPaginatorLink(paginator.hasNext(), ">>", 
				paginator.getLastPageIndex(), JWLStyleClass.LINK_LAST_PAGE);
	}

	private UIComponent getPaginatorLink(boolean viewCondition, String text, 
			int pageNumber, String styleCass) {
		if (!viewCondition) {
			HtmlFreeOutput output = new HtmlFreeOutput();
			output.setFreeOutput("");
			return output;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_LIST.id);
		params.put(JWLURLParams.LIST_PAGE_NUMBER, String.valueOf(pageNumber));
		
		HtmlLink link = this.getHtmlLink(text, params);
		link.setIsAjax(Boolean.TRUE);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, styleCass);
		return link;
	}
	
	private List<UIComponent> encodeHeaders() {
		List<JWLTableHeaders> orderableColumns = getOrderableColumns();
		List<JWLTableHeaders> headerNames = getHeaders();
		List<UIComponent> result = new ArrayList<UIComponent>();
		
		for (JWLTableHeaders tableHeader : headerNames) {
			if (orderableColumns.contains(tableHeader)) {
				result.add(this.getOrderColumnLink(tableHeader.value));
			} else {
				HtmlFreeOutput output = new HtmlFreeOutput();
				output.setFreeOutput(tableHeader.value);
				result.add(output);
			}
		}
		return result;
	}

	private HtmlLink getOrderColumnLink(String header) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_LIST.id);
		params.put(JWLURLParams.LIST_ORDER_COLUMN, header.replace(" ", "_"));
		HtmlLink link = this.getHtmlLink(header, params);
		return link;
	}

	private HtmlDiv getRatingComponent(float rating) {
		return RatingComponent.getComponent(rating);
	}

	private UIComponent getTagsComponent(String tagsString) {
		return this.getHtmlText(tagsString);
	}

	private UIComponent getEditorComponent(String editorValue) {
		return this.getHtmlText(editorValue);
	}

	private UIComponent getEditingCountComponent(int editingCountValue) {
		return this.getHtmlText(editingCountValue);
	}

	private UIComponent getCreatedComponent(String createdValue) {
		return this.getHtmlText(createdValue);
	}

	private List<JWLTableHeaders> getHeaders() {
		if (columns == null) {
			columns = new ArrayList<JWLTableHeaders>();
			columns.add(JWLTableHeaders.TITLE);
			columns.add(JWLTableHeaders.TAGS);
			columns.add(JWLTableHeaders.EDITOR);
			columns.add(JWLTableHeaders.EDITING_COUNT);
			columns.add(JWLTableHeaders.CREATED);
			columns.add(JWLTableHeaders.RATING);
			columns.add(JWLTableHeaders.ACTIONS);
		}
		return columns;
	}
	
	private List<JWLTableHeaders> getOrderableColumns() {
		if (orderableColumns == null) {
			orderableColumns = new ArrayList<JWLTableHeaders>();
			orderableColumns.add(JWLTableHeaders.TITLE);
			orderableColumns.add(JWLTableHeaders.EDITOR);
			orderableColumns.add(JWLTableHeaders.CREATED);
		}
		return orderableColumns;
	}
}
