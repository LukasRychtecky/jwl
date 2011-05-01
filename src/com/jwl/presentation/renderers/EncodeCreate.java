package com.jwl.presentation.renderers;

import com.jwl.business.security.IIdentity;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;
import java.util.Map;

public class EncodeCreate extends AbstractEncoder {

	private HtmlAppForm form;

	public EncodeCreate(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
		this.form = (HtmlAppForm) params.get("form");
	}

	@Override
	public List<UIComponent> getEncodedComponent(){
		List<UIComponent> components = new ArrayList<UIComponent>();
		components.add(this.form);
		components.add(encodeArticleSuggestorDiv());
		
		HtmlDiv navigation = new HtmlDiv();
		navigation.addStyleClass("jwl-navigation");
		HtmlLink link = new HtmlLink();
		link.setText("Back to listing");
		link.setIsAjax(Boolean.TRUE);
		link.setStyleClass("jwl-action-button");
		link.setValue(this.linker.buildLink("default"));
		navigation.getChildren().add(link);
		components.add(navigation);
		return components;
	}

	protected HtmlDiv encodeArticleSuggestorDiv(){
		HtmlDiv div = new HtmlDiv();
		div.addStyleClass(JWLStyleClass.SUGGESTOR_DIV);
		return div;
	}

}
