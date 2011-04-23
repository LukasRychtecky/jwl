package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;

public class EncodeTopicCreate extends AbstractEncoder {

	private ArticleTO article;
	
	public EncodeTopicCreate() {
		super();
		this.article = (ArticleTO) context.getAttributes().get(JWLContextKey.ARTICLE);
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.encodeForm());
		return components;
	}
	
	private HtmlActionForm encodeForm() {
		Map<String, String> params = parser.getURLParametersMinusArticleParameters();
		params.put(JWLURLParams.ARTICLE_TITLE, article.getTitle());
		params.put(JWLURLParams.DO, JWLActions.FORUM_TOPIC_CREATE.id);
		params.put(JWLURLParams.STATE, JWLStates.FORUM_TOPIC_LIST.id);
		
		HtmlActionForm form = new HtmlActionForm();
		form.setId(JWLElements.FORUM_CREATE_TOPIC_FORM.id);
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.linker.buildLink(params));
		
		form.getChildren().add(this.encodedPanel());
		form.getChildren().add(this.encodedPanelActions());
		
		return form;
	}
	
	private HtmlDiv encodedPanel() {
		HtmlDiv panel = new HtmlDiv();
		panel.setStyleClass(JWLStyleClass.PANEL);

		HtmlDiv panelHeader = new HtmlDiv();
		panelHeader.setStyleClass(JWLStyleClass.PANEL_HEADER);
		panelHeader.setValue("Create new Topic:");
		
		HtmlDiv panelBody = new HtmlDiv();
		panelBody.setStyleClass(JWLStyleClass.PANEL_BODY);
		panelBody.addChildren(this.encodePanelBody());
		
		List<UIComponent> panelChildern =  panel.getChildren();
		panelChildern.add(panelHeader);
		panelChildern.add(panelBody);
		
		return panel;
	}
	
	private HtmlDiv encodedPanelActions() {
		HtmlDiv buttonsPanel = new HtmlDiv();
		buttonsPanel.setStyleClass(JWLStyleClass.PANEL_ACTION_BUTTONS);
		List<UIComponent> panelChildren = buttonsPanel.getChildren();
		panelChildren.add(encodedCreateButton());
		panelChildren.add(encodedCancelButton());
		
		return buttonsPanel;
	}
	
	private List<UIComponent> encodePanelBody() {
		List<UIComponent> panelComponets = new ArrayList<UIComponent>();
		panelComponets.add(this.encodedSubject());
		panelComponets.add(this.encodedText());
		return panelComponets;
	}
	
	private UIComponent encodedSubject() {
		HtmlInputText livabilityInput = new HtmlInputText();
		livabilityInput.setSize(4);
		livabilityInput.setId(JWLElements.FORUM_SUBJECT.id);
		
		HtmlOutputLabel labelForFileName = new HtmlOutputLabel();
		labelForFileName.setFor(JWLElements.FORUM_SUBJECT.id);
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
		
	private UIComponent encodedText() {
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		textArea.setRows(15);
		textArea.setCols(40);
		textArea.setId(JWLElements.FORUM_TOPIC_TEXT.id);
		return textArea;
	}
	
	private UIComponent encodedCreateButton(){
		HtmlCommandButton button = new HtmlCommandButton();
		button.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		button.setType("submit");
		button.setId(JWLElements.FORUM_TOPIC_CREATE.id);
		button.setValue(JWLElements.FORUM_TOPIC_CREATE.value);
		return button;		
	}
	
	private UIComponent encodedCancelButton(){
		HtmlCommandButton button = new HtmlCommandButton();
		//button.setStyleClass(JWLStyleClass.ACTION_BUTTON_SMALLER);
		button.setType("submit");
		button.setId(JWLElements.FORUM_TOPIC_CANCEL.id);
		button.setValue(JWLElements.FORUM_TOPIC_CANCEL.value);
		return button;		
	}

}
