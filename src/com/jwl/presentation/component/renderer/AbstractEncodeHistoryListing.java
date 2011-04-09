package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.util.html.component.HtmlHeaderPanelGrid;
import com.jwl.util.html.component.HtmlLinkProperties;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractEncodeHistoryListing extends JWLEncoder {

	private final String[] headers = new String[]{"Modified", "Change note", "Actions"};

	protected List<String> getHeaderNames() {
		return Arrays.asList(this.headers);
	}

	protected void encodeListing(List<HistoryTO> histories, List<String> headers) throws IOException {
		HtmlPanelGrid table = getTable(headers);
		List<UIComponent> articlesTableData = table.getChildren();
		for (HistoryTO history : histories) {
			this.encodeRowData(history, articlesTableData);
		}
		table.encodeAll(this.context);
	}


	protected void encodeLinkToArticleListing() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to listing");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_BACK);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(super.context);
	}

	protected void encodeRowData(HistoryTO history,	List<UIComponent> tableData) {

		tableData.add(this.getLinkComponent(history.getModified().toString(), history.getId(), ArticleActions.HISTORY_VIEW));
		tableData.add(this.getLinkComponent(history.getChangeNote(), history.getId(), ArticleActions.HISTORY_VIEW));
		tableData.add(this.getLinkComponent("Restore", history.getId(), ArticleActions.RESTORE));
	}

	protected UIComponent getLinkComponent(String title, HistoryId id, String action) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(title);
		properties.addParameter(JWLURLParameters.ACTION, action);
		properties.addParameter(JWLURLParameters.HISTORY_ID, id.getId());
		properties.addParameter(JWLURLParameters.ARTICLE_ID, id.getArticleId().getId());
		//properties.addClass(JWLStyleClass.ACTION_BUTTON_STYLE);
		return super.getHtmlLinkComponent(properties);
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

}
