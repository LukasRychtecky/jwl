package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.component.renderer.FlashMessage;
import com.jwl.presentation.component.renderer.FlashMessageType;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.html.AppForm;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlInputExtended;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.util.html.component.HtmlActionForm;
import com.jwl.util.html.component.HtmlDivCommandButton;
import com.jwl.util.html.component.HtmlDivInputText;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
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
//		this.createForm();

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.build("detail"));
		link.setText("CLick mee");
		this.container.add(link);

		if (this.form != null) {
			for (Entry<String, HtmlInputExtended> entry : this.form.getInputs().entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue().getValue());
			}
		}
	}

	public void renderDetail() throws IOException {
		HtmlAppForm form = this.createFormMujForm();
		super.container.add(form);
		this.renderer.renderDetail();
	}

	public void formValid() {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Hledas: " + this.getFormValue(JWLElements.SEARCH_INPUT.id));
		super.container.add(message);
	}

	public HtmlAppForm createFormMujForm() {
		HtmlAppForm form = new HtmlAppForm("MujForm");
		form.addHidden("hidden", "Hidden", "default");
		form.addPassword("pass", "Pass");
		form.addText("text", "Text", "default");
		form.addCheckbox("checkbox", "Checkbox");
		form.addTextArea("textarea", "Textarea", "blaaaaah");
		form.addSubmit("submit", "Send", "click");
		form.setAction(this.linker.build("default"));
		return form;
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
