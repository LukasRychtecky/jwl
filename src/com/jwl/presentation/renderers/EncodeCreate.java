package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlAppForm;

public class EncodeCreate extends AbstractEncodeEdit {
	
	private HtmlAppForm form;

	public EncodeCreate(HtmlAppForm form) {
		super();
		this.form = form;
	}

	@Override
	public List<UIComponent> getEncodedComponent() {
		List<UIComponent> components = new ArrayList<UIComponent>(); 
		components.add(this.form);
		return components;
	}

	@Override
	protected List<UIComponent> encodedFormContent() {
		List<UIComponent> component = new ArrayList<UIComponent>();
		
		component.add(this.encodedLabelForTitleInCreate());
		component.add(this.encodedTitleInCreate());
		
		ArticleTO article = new ArticleTO();
		component.addAll(this.encodedCommonContent(article));
		
		component.add(this.getHtmlSubmitComponent(JWLElements.CREATE_SAVE,
				JWLStyleClass.EDIT_INPUT_SUBMIT));
		
		return component;
	}
	
	private UIComponent encodedLabelForTitleInCreate() {
		return getHtmlLabelComponent(JWLElements.EDIT_TITLE,
				JWLStyleClass.EDIT_LABEL_FOR_TITLE);
	}
	
	private UIComponent encodedTitleInCreate() {
		return getHtmlInputComponent(JWLElements.EDIT_TITLE, "", 
				JWLStyleClass.EDIT_TITLE_INPUT);
	}

}
