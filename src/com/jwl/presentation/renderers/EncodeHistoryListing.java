package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlHeaderPanelGrid;
import com.jwl.presentation.html.HtmlLink;

/**
 *
 * @author Lukas Rychtecky
 */
public class EncodeHistoryListing extends AbstractEncoder {

	private final String[] headers = new String[]{"Modified", "Change note", "Actions"};
	
	private ArticleTO article;
	List<HistoryTO> histories;
	
	public EncodeHistoryListing(ArticleTO article, List<HistoryTO> histories) {
		super();
		this.article = article;
		this.histories = histories;
	}
	
	private List<UIComponent> getHeaderNames() {
		List<UIComponent> result = new ArrayList<UIComponent>();
		for(String header : headers) {
			HtmlFreeOutput output = new HtmlFreeOutput();
			output.setFreeOutput(header);
			result.add(output);
		}
		return result;
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		if (!histories.isEmpty()) {
			components.add(this.encodedListing());
		} else {
			components.add(this.getHtmlText("No history found."));
		}
		components.add(encodedLinkToArticleListing());
		return components;
	}

	private HtmlPanelGrid encodedListing() {
		HtmlPanelGrid table = getTable(getHeaderNames());
		List<UIComponent> articlesTableData = table.getChildren();
		for (HistoryTO history : histories) {
			this.encodedRowData(history, articlesTableData);
		}
		return table;
	}

	private void encodedRowData(HistoryTO history, List<UIComponent> tableData) {
		tableData.add(this.encodedLinkToView(history));
		
		tableData.add(this.getHtmlText(history.getChangeNote()));
		
		tableData.add(this.encodedLinkToRestore("Restore", history.getId()));
	}
	
	private HtmlLink encodedLinkToView(HistoryTO history){
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.HISTORY_VIEW.id);
		params.put(JWLURLParams.HISTORY_ID, String.valueOf(history.getId().getId()));
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
		
		HtmlLink link = getHtmlLink(history.getModified().toString(), params);
		return link;
	}
	
	private HtmlLink encodedLinkToRestore(String text, HistoryId historyId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		params.put(JWLURLParams.DO, JWLActions.HISTORY_RESTORE.id);
		params.put(JWLURLParams.HISTORY_ID, historyId.getId().toString());
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
		
		HtmlLink link = getHtmlLink(text, params);
		return link;
	}
	
	private HtmlLink encodedLinkToArticleListing() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ARTICLE_LIST.id);
		
		HtmlLink link = getHtmlLink("Back to listing", params);
		link.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_BACK);
		return link;
	}

	private HtmlPanelGrid getTable(List<UIComponent> headers) {
		HtmlHeaderPanelGrid table = new HtmlHeaderPanelGrid();
		table.setColumns(headers.size());
		table.setHeaders(headers);
		table.setStyleClass(JWLStyleClass.TABLE_OF_ARTICLES);
		table.setHeaderClass(JWLStyleClass.TABLE_HEADER_OF_ARTICLES);
		return table;
	}

	
}
