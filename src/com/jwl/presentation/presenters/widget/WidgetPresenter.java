package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDivCommandButton;
import com.jwl.util.html.component.HtmlDivInputText;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetPresenter extends AbstractPresenter {

	private Renderer renderer;

	public WidgetPresenter(FacesContext context) {
		super(context);
		this.renderer = new Renderer(this.context, super.linker, super.container);
	}

	@Override
	public void renderDefault() throws IOException {
		this.renderer.renderDefault();
		this.createForm();

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.build("detail"));
		link.setText("CLick mee");
		this.container.add(link);
	}

	public void renderDetail() throws IOException {
		this.renderer.renderDetail();
	}

	public void formValid() {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Hledas: " + this.getFormValue(JWLElements.SEARCH_INPUT.id));
		super.container.add(message);
	}

	private void createForm() {

		HtmlActionForm form = new HtmlActionForm();
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
		super.container.add(form);
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
