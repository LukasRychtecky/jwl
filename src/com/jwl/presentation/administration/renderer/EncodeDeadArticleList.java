package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.administration.enumerations.AdministrationActions;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDivOutputLabel;
import com.jwl.util.html.component.HtmlFreeOutput;
import com.jwl.util.html.component.HtmlHeaderPanelGrid;
import com.jwl.util.html.component.HtmlLinkProperties;

public class EncodeDeadArticleList extends JWLEncoder {
	private final String[] headers = new String[] { "", "Title", "Tags", "Editor",
			"Editing count", "Created", "Rating" };

	public EncodeDeadArticleList(IFacade facade) {
		super(facade);
	}
	
	@Override
	public void encodeResponse() {
		try {
			super.encodeFlashMessages();
			List<ArticleTO> deadArticles = this.facade.getDeadArticles();
			encodeForm(deadArticles);
			encodeLinkToListing();
		} catch (Exception e) {
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE,
					null, e);
			super.addImplicitErrorFlashMessage();
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeListing.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	private List<String> getHeaderNames() {
		List<String> result = new ArrayList<String>();
		for (String s : headers) {
			result.add(s);
		}
		return result;
	}
	
	private void encodeForm(List<ArticleTO> articles) throws IOException{
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.getFormAction());
		List<UIComponent> formData = form.getChildren();
		HtmlPanelGrid table = encodeListing(articles);
		formData.add(table);
		
		addLivabilityInput(formData);
		encodeFormActions(formData);
		form.encodeAll(context);
	}
	
	private void addLivabilityInput(List<UIComponent> formData){
		HtmlInputText livabilityInput = new HtmlInputText();
		livabilityInput.setSize(4);
		livabilityInput.setId(JWLElements.KNOWLEDGE_LIVABILITY_INPUT.id);
		HtmlDivOutputLabel labelForFileName = new HtmlDivOutputLabel();
		//labelForFileName.setDivStyleClass(styleClass);
		labelForFileName.setFor(JWLElements.KNOWLEDGE_LIVABILITY_INPUT.id);
		labelForFileName.setValue("livability increase");
		formData.add(labelForFileName);
		formData.add(livabilityInput);
	}
	
	private void encodeFormActions(List<UIComponent> formData){
		HtmlPanelGrid table= new HtmlPanelGrid();
		table.setColumns(2);
		table.setCellpadding("0");
		table.setCellspacing("0");
		List<UIComponent> tableData=table.getChildren();
		
		tableData.add(this.getHtmlSubmitComponent(JWLElements.KNOWLEDGE_DEAD_DELETE,
				JWLStyleClass.EDIT_INPUT_SUBMIT));
		tableData.add(this.getHtmlSubmitComponent(JWLElements.KNOWLEDGE_INCREASE_LIVABILITY,
				JWLStyleClass.EDIT_INPUT_SUBMIT));
		formData.add(table);
	}

	private HtmlPanelGrid encodeListing(List<ArticleTO> articles)
			throws IOException {
		HtmlPanelGrid table = getTable(getHeaderNames());
		List<UIComponent> articlesTableData = table.getChildren();
		for (ArticleTO article : articles) {
			this.encodeRowData(article, articlesTableData);
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

	private void encodeRowData(ArticleTO article,
			List<UIComponent> articlesTableData) {

		HtmlSelectBooleanCheckbox chbx = new HtmlSelectBooleanCheckbox();
		chbx.setId(JWLElements.KNOWLEDGE_ID_CHECKBOX.id+article.getId().getId().intValue());
		articlesTableData.add(chbx);
		
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
		properties.addParameter(JWLURLParameters.ACTION,
				AdministrationActions.DEAD_ARTICLE_VIEW.action);
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

	private void encodeLinkToListing() throws IOException {
		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.setValue("Back to listing");
		properties.addParameter(JWLURLParameters.ACTION, ArticleActions.LIST);
		properties.addClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		properties.addClass(JWLStyleClass.VIEW_LINK_BACK);

		HtmlOutputLink link = getHtmlLinkComponent(properties);
		link.encodeAll(context);
	}
	
	@Override
	protected String getFormAction() {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String target = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParameters.ACTION, AdministrationActions.DEAD_ARTICLE_LIST.action);
		return getFormActionString(context, target, params);
	}
}
