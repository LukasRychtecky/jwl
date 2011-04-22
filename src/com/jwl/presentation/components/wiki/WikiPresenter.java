package com.jwl.presentation.components.wiki;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.presentation.components.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.enumerations.JWLStyleClass;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.html.HtmlActionForm;
import com.jwl.presentation.html.HtmlDiv;
import com.jwl.presentation.url.RequestMapDecoder;

/**
 *
 * @author Lukas Rychtecky
 */
public class WikiPresenter extends AbstractPresenter {


	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Ahoj ja jsem nova komponenta");
		message.encodeAll(this.context);

		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, "detail");
		
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.buildLink(params));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na detail");
		link.getChildren().add(text);
		link.encodeAll(context);
		
		this.createForm();
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Renderujeme detail!");
		message.encodeAll(this.context);

		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParams.STATE, "default");
		
		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.buildLink(params));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na default");
		link.getChildren().add(text);
		link.encodeAll(context);
	}

	public void decodeFormValid() {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.JWL_DIV);
		String searchText = decoder.getValue(JWLElements.SEARCH_INPUT);
		
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Hledas: " + searchText);
		try {
			message.encodeAll(this.context);
		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void createForm() {

		HtmlActionForm form = new HtmlActionForm();
		form.setAction(this.linker.buildForm("formValid"));

		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);
		table.setBorder(0);
		List<UIComponent> children = table.getChildren();
		children.add(this.getHtmlInputComponent("", JWLElements.SEARCH_INPUT,
				JWLStyleClass.SEARCH_INPUT));
		children.add(this.getHtmlSubmitComponent(JWLElements.SEARCH_BUTTON,
				JWLStyleClass.SEARCH_INPUT_SUBMIT));


		form.getChildren().add(table);
		try {
			form.encodeAll(context);
		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	protected HtmlDiv getHtmlSubmitComponent(JWLElements element, String styleClass) {
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(styleClass);
		
		HtmlCommandButton submit = new HtmlCommandButton();
		submit.setType("submit");
		submit.setId(element.id);
		submit.setValue(element.value);
		
		div.addChildren(submit);
		return div;
	}

	protected HtmlDiv getHtmlInputComponent(String value, JWLElements element, 
			String styleClass) {
		
		HtmlDiv div = new HtmlDiv();
		div.setStyleClass(styleClass);
		
		HtmlInputText inputText = new HtmlInputText();
		inputText.setValue(value);
		inputText.setId(element.id);
		
		div.addChildren(inputText);
		return div;
	}
}
