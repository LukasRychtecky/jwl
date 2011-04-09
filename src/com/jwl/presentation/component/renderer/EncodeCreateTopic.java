package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.core.Linker;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDiv;

public class EncodeCreateTopic extends JWLEncoder{

	private ArticleId articleId;
	private Linker linker;
	
	public EncodeCreateTopic(ArticleId articleId, Linker linker) {
		this.articleId = articleId;
		this.linker = linker;
	}

	@Override
	public void encodeResponse() {
		try {
			super.encodeFlashMessages();
			encodeForm();
		} catch (IOException e) {
			Logger.getLogger(EncodeHistoryView.class.getName()).log(Level.SEVERE, null, e);
			super.addImplicitErrorFlashMessage();
		} catch (ModelException e) {
			super.addImplicitErrorFlashMessage();
			Logger.getLogger(EncodeHistoryView.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeHistoryView.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}
	
	protected void encodeForm() throws IOException, ModelException {
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildForm("topicSave"));
//		form.setAction(this.getFormAction());
		encodePanel(form.getChildren());
		encodePanelActions(form.getChildren());
		form.encodeAll(context);
	}
	
	protected void encodePanel(List<UIComponent> formData) throws IOException{
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);
		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Create new Topic:");
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		encodePanelBody(panelBody.getChildren());
		List<UIComponent> panelChildern =  panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		formData.add(panel);
	}
	
	protected void encodePanelActions(List<UIComponent> formData){
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(encodeCreateButton());
		panelChildren.add(encodeCancelButton());
		formData.add(buttonsPanel);
	}
	
	protected void encodePanelBody(List<UIComponent> panelComponets) throws IOException{
		panelComponets.add(encodeSubject());
		panelComponets.add(encodeText());
		panelComponets.add(encodeHiddenArticleId());
	}
	
	protected UIComponent encodeSubject() throws IOException {
		HtmlInputText livabilityInput = new HtmlInputText();
		livabilityInput.setSize(4);
		livabilityInput.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id+"-"+JWLElements.FORUM_SUBJECT.id);
		HtmlOutputLabel labelForFileName = new HtmlOutputLabel();
		//labelForFileName.setDivStyleClass(styleClass);
		labelForFileName.setFor(JWLElements.FORUM_CREATE_TOPIC_FORM.id+"-"+JWLElements.FORUM_SUBJECT.id);
		labelForFileName.setValue("Subject");
		HtmlPanelGrid table= new HtmlPanelGrid();
		table.setColumns(2);
		table.setCellpadding("0");
		table.setCellspacing("0");
		List<UIComponent> tableData=table.getChildren();
		tableData.add(labelForFileName);
		tableData.add(livabilityInput);
		return table;
	}
		
	protected UIComponent encodeHiddenArticleId() {
		HtmlInputHidden hiddenArticleId = new HtmlInputHidden();
		hiddenArticleId.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id+"-"+JWLElements.FORUM_ARTICLE_ID.id);
		hiddenArticleId.setValue(articleId.getId());
		return hiddenArticleId;
	}
	
	protected UIComponent encodeText() throws IOException {
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		textArea.setRows(15);
		textArea.setCols(40);
		textArea.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id+"-"+JWLElements.FORUM_TOPIC_TEXT.id);
		return textArea;
	}
	
	protected UIComponent encodeCreateButton(){
		HtmlCommandButton button = new HtmlCommandButton();
		button.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		button.setType("submit");
		button.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id+"-"+JWLElements.FORUM_TOPIC_CREATE.id);
		button.setValue(JWLElements.FORUM_TOPIC_CREATE.value);
		return button;		
	}
	
	protected UIComponent encodeCancelButton(){
		HtmlCommandButton button = new HtmlCommandButton();
		//button.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		button.setType("submit");
		button.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id+"-"+JWLElements.FORUM_TOPIC_CANCEL.id);
		button.setValue(JWLElements.FORUM_TOPIC_CANCEL.value);
		return button;		
	}
	
	@Override
	protected String getFormAction() {
		WikiURLParser parser = new WikiURLParser();
		String context = parser.getCurrentContext();
		String target = parser.getCurrentPage();
		Map<String, String> params = parser.getURLParametersMinusArticleParameters();
		params.put(JWLURLParameters.ACTION, ArticleActions.FORUM_TOPIC_LIST);
		params.put(JWLURLParameters.ARTICLE_ID, articleId.getId().toString());
		return getFormActionString(context, target, params);
	}
		
}
