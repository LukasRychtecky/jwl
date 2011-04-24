package com.jwl.presentation.components.widget;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlLink;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetPresenter extends AbstractPresenter {

	private WidgetRenderer renderer;

	public WidgetPresenter() {
		super();
		this.renderer = new WidgetRenderer(this.context, super.linker, super.container);
	}

	@Override
	public void renderDefault() throws IOException {
		this.renderer.renderDefault();
	}

	public void renderDetail() throws IOException {
		HtmlAppForm form = this.createFormMujForm();
		super.container.add(form);
		this.renderer.renderDetail();
	}

	public void decodeMujForm() {
		HtmlAppForm form = (HtmlAppForm) super.form;

		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("Skryte pole");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue(form.get("hidden").getValue().toString());
		table.getChildren().add(text);

		text = new HtmlOutputText();
		text.setValue("Heslo");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue(form.get("pass").getValue());
		table.getChildren().add(text);

		text = new HtmlOutputText();
		text.setValue("Text");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue(form.get("text").getValue());
		table.getChildren().add(text);

		text = new HtmlOutputText();
		text.setValue("Checkbox");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue(form.get("checkbox").getValue());
		table.getChildren().add(text);

		text = new HtmlOutputText();
		text.setValue("Textarea");
		table.getChildren().add(text);
		text = new HtmlOutputText();
		text.setValue(form.get("textarea").getValue());
		table.getChildren().add(text);

		super.container.add(table);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.buildLink("default"));
		link.setText("ja jsem AJAXovy odkaz na default");
		link.setIsAjax(Boolean.TRUE);
		super.container.add(link);
		
	}

	public HtmlAppForm createFormMujForm() {
		HtmlAppForm form = new HtmlAppForm("MujForm");
		form.addHidden("hidden", "defaultValue");
		form.addPassword("pass", "Heslo");
		form.addText("text", "Text", "defaultValue");
		form.addCheckbox("checkbox", "Checkbox");
		form.addTextArea("textarea", "Textarea", "blaaaaah");
		form.addSubmit("submit", "Odesli", "click");
		form.setAction(this.linker.buildForm("mujForm", null));
		return form;
	}

}
