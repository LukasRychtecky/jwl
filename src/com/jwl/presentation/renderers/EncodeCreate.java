package com.jwl.presentation.renderers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlDiv;

public class EncodeCreate extends AbstractEncoder {

	private HtmlAppForm form;

	public EncodeCreate(HtmlAppForm form) {
		super();
		this.form = form;
	}

	@Override
	public List<UIComponent> getEncodedComponent(){
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(this.form);
		components.add(encodeArticleSuggestorDiv());
		return components;
	}

	protected HtmlDiv encodeArticleSuggestorDiv(){
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.SUGGESTOR_DIV);
		return div;
	}

}
