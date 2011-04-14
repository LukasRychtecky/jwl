package com.jwl.presentation.html;

import com.jwl.util.html.component.HtmlInputFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlAppForm extends HtmlOutputText implements AppForm {

	public static final String ELEMENT = "form";
	public static final String METHOD_POST = "post";
	public static final String METHOD_GET = "get";

	protected String action;
	protected String method;
	protected String enctype;
	protected String id;
	protected String name;
	protected Map<String, HtmlInputExtended> components;

	public HtmlAppForm(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Form name can't be empty or null!");
		}
		this.name = name;
		this.method = METHOD_POST;
		this.enctype = "application/x-www-form-urlencoded";
		this.components = new HashMap<String, HtmlInputExtended>();
	}

	@Override
	public String getAction() {
		return action;
	}

	@Override
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getEnctype() {
		return enctype;
	}

	@Override
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(ELEMENT, this);
		writer.writeAttribute("action", this.action, null);
		writer.writeAttribute("enctype", this.enctype, null);
		writer.writeAttribute("method", this.method, null);
		writer.writeAttribute("id", this.id, null);
		
		HtmlInputHidden formId = new HtmlInputHidden();
		formId.setId(FORM_NAME);
		formId.setValue(this.name);
		formId.encodeAll(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.endElement(ELEMENT);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);
		for (HtmlInputExtended input : this.components.values()) {

			if (input.getComponent() instanceof HtmlInputHidden) {
				input.getComponent().encodeAll(context);
				continue;
			}

			String label = input.getLabel();

			if (label != null) {
				table.getChildren().add(this.createLabel(label, input.getComponent().getId()));
			} else {
				table.getChildren().add(new HtmlOutputText());
			}
			table.getChildren().add(input.getComponent());
		}
		table.encodeAll(context);
	}

	private ResponseWriter getWriter(FacesContext context) throws IOException {
		return context.getResponseWriter();
	}

	@Override
	public UIComponent addText(String name, String label, String value) {
		HtmlInputText input = new HtmlInputText();
		input.setId(this.createName(name));
		input.setValue(value);
		input.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(input, label);
		this.components.put(name, labeled);
		return input;
	}

	@Override
	public UIComponent addFile(String name, String label) {
		HtmlInputFile input = new HtmlInputFile();
		input.setId(this.createName(name));
		input.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(input, label);
		this.components.put(name, labeled);
		return input;
	}

	@Override
	public UIComponent addPassword(String name, String label) {
		HtmlInputSecret input = new HtmlInputSecret();
		input.setId(this.createName(name));
		input.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(input, label);
		this.components.put(name, labeled);
		return input;
	}

	@Override
	public UIComponent addHidden(String name, String label, String value) {
		HtmlInputHidden input = new HtmlInputHidden();
		input.setId(this.createName(name));
		input.setValue(value);
		HtmlInputExtended extended = new HtmlInputExtended(input, null);
		this.components.put(name, extended);
		return input;
	}

	@Override
	public UIComponent addTextArea(String name, String label, String value) {
		HtmlInputTextarea textarea = new HtmlInputTextarea();
		textarea.setId(this.createName(name));
		textarea.setValue(value);
		textarea.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(textarea, label);
		this.components.put(name, labeled);
		return textarea;
	}

	@Override
	public UIComponent addSubmit(String name, String caption, String label) {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(this.createName(name));
		button.setValue(caption);
		button.setLabel(label);
		HtmlInputExtended extended = new HtmlInputExtended(button, null);
		this.components.put(name, extended);
		return button;
	}

	@Override
	public UIComponent addCheckbox(String name, String caption) {
		HtmlSelectBooleanCheckbox checkbox = new HtmlSelectBooleanCheckbox();
		checkbox.setId(this.createName(name));
		checkbox.setLabel(caption);
		checkbox.setValue(false);
		HtmlInputExtended labeled = new HtmlInputExtended(checkbox, caption);
		this.components.put(name, labeled);
		return checkbox;
	}

	protected String createName(String name) {
		return PREFIX + name;
	}

	protected UIComponent createLabel(String label, String id) {
		HtmlOutputLabel comp = new HtmlOutputLabel();
		comp.setValue(label);
		comp.setFor(id);
		return comp;
	}

	@Override
	public HtmlInputExtended get(String name) {
		return this.components.get(name);
	}

	@Override
	public Map<String, HtmlInputExtended> getInputs() {
		return this.components;
	}

}
