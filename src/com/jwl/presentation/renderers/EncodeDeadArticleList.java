package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlFreeOutput;
import com.jwl.presentation.html.HtmlHeaderPanelGrid;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.renderers.units.RatingComponent;
import com.jwl.presentation.url.Linker;

public class EncodeDeadArticleList extends AbstractEncoder {
	private final String[] headers = new String[] { "", "Title", "Tags", "Editor",
			"Editing count", "Created", "Rating" };

	private List<ArticleTO> deadArticles;
	
	@SuppressWarnings("unchecked")
	public EncodeDeadArticleList(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.deadArticles = (List<ArticleTO>) params.get("deadArticles"); 
	}
	
	private List<UIComponent> getHeaders() {
		List<UIComponent> result = new ArrayList<UIComponent>();
		for (String header : headers) {
			HtmlFreeOutput output = new HtmlFreeOutput();
			output.setFreeOutput(header);
			result.add(output);
		}
		return result;
	}
	
	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodedForm());
		return components;
	}
	
	private HtmlActionForm encodedForm() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.DEAD_ARTICLE_LIST.id);
		params.put(JWLURLParams.DO, JWLActions.DEAD_ARTICLE.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.KNOWLEDGE_DEAD_SUG_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));
		
		form.getChildren().add(this.encodedPanel(deadArticles));
		form.getChildren().add(this.encodedPanelActions());		
		
		return form;
	}
	
	protected HtmlDiv encodedPanel(List<ArticleTO> articles) {
		HtmlDiv panel = new HtmlDiv();
		panel.addStyleClass(JWLStyleClass.PANEL);
		
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.addStyleClass(JWLStyleClass.PANEL_HEADER);
		HtmlOutputText title = getHtmlText("Possibly dead articles");
		panelHeader.addChildren(title);
		
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.addStyleClass(JWLStyleClass.PANEL_BODY);
		HtmlPanelGrid table = encodedListing(articles);
		panelBody.getChildren().add(table);
		//panelBody.getChildren().add(getPageButtonsComponent(paginator));
		List<UIComponent> panelChildern =  panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		
		return panel;
	}
	
	private HtmlPanelGrid encodedListing(List<ArticleTO> articles) {
		HtmlHeaderPanelGrid table = new HtmlHeaderPanelGrid();
		table.setColumns(getHeaders().size());
		table.setCellpadding("0");
		table.setCellspacing("0");
		table.setHeaders(getHeaders());
		table.setStyleClass(JWLStyleClass.TABLE_OF_ARTICLES);
		table.setHeaderClass(JWLStyleClass.TABLE_HEADER_OF_ARTICLES);
		
		List<UIComponent> articlesTableData = table.getChildren();
		for (ArticleTO article : articles) {
			articlesTableData.addAll(this.encodeRowData(article));
		}
		return table;
	}

	private List<UIComponent> encodeRowData(ArticleTO article) {
		List<UIComponent> components = new ArrayList<UIComponent>();
		
		HtmlSelectBooleanCheckbox chbx = new HtmlSelectBooleanCheckbox();
		chbx.setId(JWLElements.KNOWLEDGE_ID_CHECKBOX.id+article.getId().getId().intValue());
		
		components.add(chbx);
		components.add(this.encodedArticleLinkComponent(article.getTitle()));

		StringBuilder tags = new StringBuilder();
		for (String tag : article.getTags()) {
			tags.append(tag).append(", ");
		}

		String separatedTags = "";
		if (tags.length() > 0) {
			separatedTags = tags.substring(0, tags.length() - 2);
		}

		components.add(this.encodedTagsComponent(separatedTags));
		components.add(this.encodedEditorComponent(article.getEditor()));
		components.add(this.encodedEditingCountComponent(article.getEditCount()));
		components.add(this.encodedCreatedComponent(article.getCreated().toString()));
		components.add(this.encodedRatingComponent(article.getRatingAverage()));
		
		return components;
	}

	private HtmlLink encodedArticleLinkComponent(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, title);
		params.put(JWLURLParams.STATE, JWLStates.DEAD_ARTICLE_VIEW.id);
		return this.getHtmlLink(title, params);
	}

	private HtmlOutputText encodedTagsComponent(String tagsString) {
		return this.getHtmlText(tagsString);
	}

	private HtmlOutputText encodedEditorComponent(String editorValue) {
		return this.getHtmlText(editorValue);
	}

	private HtmlOutputText encodedEditingCountComponent(int editingCountValue) {
		return this.getHtmlText(editingCountValue);
	}

	private HtmlOutputText encodedCreatedComponent(String createdValue) {
		return this.getHtmlText(createdValue);
	}

	private HtmlDiv encodedRatingComponent(float rating) {
		return RatingComponent.getStarComponent(rating);
	}

	protected HtmlDiv encodedPanelActions(){
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.addStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(encodedLivabilityInput());
		panelChildren.add(encodedLinkToAdministrationConsole());
		panelChildren.add(encodedDeleteButton());
		panelChildren.add(encodedLivabilityIncreaseButton());		
		return buttonsPanel;
	}
	
	protected HtmlPanelGrid encodedLivabilityInput(){
		HtmlInputText livabilityInput = new HtmlInputText();
		livabilityInput.setSize(4);
		livabilityInput.setId(JWLElements.KNOWLEDGE_LIVABILITY_INPUT.id);
		HtmlOutputLabel labelForFileName = new HtmlOutputLabel();
		//labelForFileName.setDivStyleClass(styleClass);
		labelForFileName.setFor(JWLElements.KNOWLEDGE_LIVABILITY_INPUT.id);
		labelForFileName.setValue("livability increase");
		HtmlPanelGrid table= new HtmlPanelGrid();
		table.setColumns(2);
		table.setCellpadding("0");
		table.setCellspacing("0");
		List<UIComponent> tableData=table.getChildren();
		tableData.add(labelForFileName);
		tableData.add(livabilityInput);
		return table;
	}
	
	protected HtmlLink encodedLinkToAdministrationConsole() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, JWLStates.ADMINISTRATION_CONSOLE.id);

		HtmlLink htmlLink = getHtmlLink("Back to administration", params);
		htmlLink.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_BACK);
		
		return htmlLink;
	}
	
	protected HtmlCommandButton encodedDeleteButton(){
		HtmlCommandButton submit = new HtmlCommandButton();
		submit.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		submit.setType("submit");
		submit.setId(JWLElements.KNOWLEDGE_DEAD_DELETE.id);
		submit.setValue(JWLElements.KNOWLEDGE_DEAD_DELETE.value);
		return submit;
	}
	
	protected HtmlCommandButton encodedLivabilityIncreaseButton(){
		HtmlCommandButton submit = new HtmlCommandButton();
		submit.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		submit.setType("submit");
		submit.setId(JWLElements.KNOWLEDGE_INCREASE_LIVABILITY.id);
		submit.setValue(JWLElements.KNOWLEDGE_INCREASE_LIVABILITY.value);
		return submit;
	}

	
}
