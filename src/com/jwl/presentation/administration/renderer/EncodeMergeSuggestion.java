package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.ArticlePair;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.util.html.component.HtmlFreeOutput;
import com.jwl.util.html.component.HtmlHeaderPanelGrid;
import com.jwl.util.html.component.HtmlLinkProperties;

public class EncodeMergeSuggestion extends JWLEncoder {

	private final String[] headers = new String[] { "Title", "Tags", "Editor",
			"Editing count", "Created", "Rating"};

	public EncodeMergeSuggestion(IFacade facade) {
		super(facade);
	}

	private List<String> getHeaderNames() {		
		List<String> result = new ArrayList<String>();	
		for(String s:headers){
			result.add(s);
		}
		for(String s:headers){
			result.add(s);
		}
		return result;
	}

	private void encodeListing(List<ArticlePair> articlePairs) throws IOException {
		HtmlPanelGrid table = getTable(getHeaderNames());
		List<UIComponent> articlesTableData = table.getChildren();
		for (ArticlePair articlePair : articlePairs) {
			this.encodeRowData(articlePair, articlesTableData);
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
	
	private void encodeRowData(ArticlePair articlePair, List<UIComponent> articlesTableData){
		encodeArticleRowData(articlePair.getArticle1(), articlesTableData);
		encodeArticleRowData(articlePair.getArticle2(), articlesTableData);
	}

	private void encodeArticleRowData(ArticleTO article,
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
		articlesTableData.add(this.getEditingCountComponent(article
				.getEditCount()));
		articlesTableData.add(this.getCreatedComponent(article.getCreated()
				.toString()));
		articlesTableData.add(this.getRatingComponent(article
				.getRatingAverage()));
	}

	private UIComponent getArticleLinkComponent(String title) {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue(title);
		properties.addParameter(JWLURLParameters.ARTICLE_TITLE, title);
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.VIEW);
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

	private UIComponent getRatingComponent(float rating) {
		int sn = (int) rating;
		int r = (int) (rating * 10) % 1;
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

	@Override
	public void encodeResponse() {
		List<ArticlePair> mergeSuggestions;
		try {
			mergeSuggestions = this.facade.GetMergeSuggestions();
		} catch (ModelException e) {
			return;
		}
		try {
			encodeListing(mergeSuggestions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
