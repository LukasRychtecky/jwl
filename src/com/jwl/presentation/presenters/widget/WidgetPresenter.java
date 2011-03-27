package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.core.Presenter;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDivCommandButton;
import com.jwl.util.html.component.HtmlDivInputText;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetPresenter extends Presenter {

	private WidgetRenderer renderer;

	public WidgetPresenter(FacesContext context) {
		super(context);
		this.renderer = new WidgetRenderer(this.context, super.linker);
	}

	@Override
	public void renderDefault() throws IOException {
		this.renderer.renderDefault();
		this.createForm();
	}

	public void renderDetail() throws IOException {
		this.renderer.renderDetail();
	}

	public void formValid() {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Hledas: " + this.getFormValue(JWLElements.SEARCH_INPUT.id));
		try {
			message.encodeAll(this.context);
		} catch (IOException ex) {
			Logger.getLogger(WidgetPresenter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void createForm() {

		HtmlActionForm form = new HtmlActionForm();
		form.setEnctype("application/x-www-form-urlencoded");
		form.setAction(this.buildFormLink("formValid"));

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
			Logger.getLogger(WidgetPresenter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	protected HtmlDivCommandButton getHtmlSubmitComponent(JWLElements element, String styleClass) {
		HtmlDivCommandButton submit = new HtmlDivCommandButton();
		submit.setDivStyleClass(styleClass);
		submit.setType("submit");
		submit.setId(element.id);
		submit.setValue(element.value);
		return submit;
	}

	protected HtmlDivInputText getHtmlInputComponent(String value, JWLElements elm, String styleClass) {
		HtmlDivInputText inputText = new HtmlDivInputText();
		inputText.setDivStyleClass(styleClass);
		inputText.setValue(value);
		inputText.setId(elm.id);
		return inputText;
	}
}
