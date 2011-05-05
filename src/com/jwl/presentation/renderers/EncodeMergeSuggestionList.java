package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import com.jwl.business.ArticlePair;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.security.IIdentity;
import com.jwl.presentation.core.AbstractComponent;
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

public class EncodeMergeSuggestionList extends AbstractEncoder {

	private final String[] headers = new String[] {"Title", "Tags", "Editor",
			"Editing count", "Created", "Rating"};

	private List<ArticlePair> articlePairs;
	
	@SuppressWarnings("unchecked")
	public EncodeMergeSuggestionList(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.articlePairs = (List<ArticlePair>) params.get("articlePairs"); 
		
	}
	
	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodedForm());
		return components;
	}
	
	private List<UIComponent> getHeaderNames() {		
		List<String> headerList = new ArrayList<String>();
		headerList.add("");
		for(int i = 0; i<2;i++){
			for(String header: headers){
				headerList.add(header);
			}
		}
		List<UIComponent> result = new ArrayList<UIComponent>();
		for(String header : headerList){
			HtmlFreeOutput output = new HtmlFreeOutput();
			output.setFreeOutput(header);
			result.add(output);
		}
		return result;
	}
	
	private HtmlActionForm encodedForm() {
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParams.STATE, JWLStates.MERGE_SUGGESTION_LIST.id);
		params.put(JWLURLParams.DO, JWLActions.MERGE_ARTICLE.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.KNOWLEDGE_MERGE_SUG_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setPrependId(true);
		form.setAction(this.linker.buildLink(params));
		List<UIComponent> formData = form.getChildren();
		formData.addAll(this.encodedPanel(articlePairs));
		formData.addAll(this.encodedPanelActions());
		
		return form;
	}
	
	protected List<UIComponent> encodedPanel(List<ArticlePair> articlePairs) {
		List<UIComponent> formData = new ArrayList<UIComponent>();
		
		HtmlDiv panel = new HtmlDiv();
		panel.addStyleClass(JWLStyleClass.PANEL);
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.addStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Merge Suggestion");
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.addStyleClass(JWLStyleClass.PANEL_BODY);
		HtmlPanelGrid table = encodeListing(articlePairs);
		panelBody.getChildren().add(table);
		//panelBody.getChildren().add(getPageButtonsComponent(paginator));
		List<UIComponent> panelChildern =  panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		formData.add(panel);
		
		return formData;
	}
	
	protected List<UIComponent> encodedPanelActions() {
		List<UIComponent> formData = new ArrayList<UIComponent>();
		
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.addStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(getLinkAdminConsole());
		panelChildren.add(getIgnoreButton());
		formData.add(buttonsPanel);
		
		return formData;
	}

	private HtmlPanelGrid encodeListing(List<ArticlePair> articlePairs) {		
		HtmlPanelGrid table = getTable(getHeaderNames());
		List<UIComponent> articlesTableData = table.getChildren();
		for (ArticlePair articlePair : articlePairs) {
			articlesTableData.addAll(this.encodeRowData(articlePair));			
		}
		return table;
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
	
	private List<UIComponent> encodeRowData(ArticlePair articlePair){
		List<UIComponent> articlesTableData = new ArrayList<UIComponent>();
		
		if(articlePair.getArticle1()==null||articlePair.getArticle2()==null){
			return Collections.emptyList();
		}
		HtmlSelectBooleanCheckbox chbx = new HtmlSelectBooleanCheckbox();
		chbx.setId(JWLElements.KNOWLEDGE_ID_PAIR_CHECKBOX.id
				+ AbstractComponent.JWL_HTML_ID_SEPARATOR
				+articlePair.getArticle1().getId().getId().intValue()
				+ AbstractComponent.JWL_HTML_ID_SEPARATOR
				+articlePair.getArticle2().getId().getId().intValue());
		articlesTableData.add(chbx);
		articlesTableData.addAll(this.encodedArticleRowData(articlePair.getArticle1()));
		articlesTableData.addAll(this.encodedArticleRowData(articlePair.getArticle2()));
		
		return articlesTableData;
	}

	private List<UIComponent> encodedArticleRowData(ArticleTO article) {
		List<UIComponent> articlesTableData = new ArrayList<UIComponent>();
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
		articlesTableData.add(this.getRatingComponent(article.getRatingAverage()));
		return articlesTableData;
	}

	private HtmlLink getArticleLinkComponent(String title) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.ARTICLE_TITLE, title);
		params.put(JWLURLParams.STATE, JWLStates.MERGE_SUGGESTION_VIEW.id);
		return this.getHtmlLink(title, params);
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

	private HtmlDiv getRatingComponent(float rating) {
		return RatingComponent.getStarComponent(rating);
	}
	
	protected HtmlLink getLinkAdminConsole() {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, "default");

		HtmlLink htmlLink = getHtmlLink("Back to administration", params);
		htmlLink.setStyleClasses(JWLStyleClass.ACTION_BUTTON_SMALLER, JWLStyleClass.VIEW_LINK_BACK);
		
		return htmlLink;
	}

	protected UIComponent getIgnoreButton(){
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		
		HtmlCommandButton button = new HtmlCommandButton();
		button.setType("submit");
		button.setId(JWLElements.KNOWLEDGE_IGNORE.id);
		button.setValue(JWLElements.KNOWLEDGE_IGNORE.value);
		
		div.addChildren(button);
		return div;
	}
}
