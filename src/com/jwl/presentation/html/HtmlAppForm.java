package com.jwl.presentation.html;

import com.jwl.presentation.forms.JSValidation;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlAppForm extends HtmlOutputText {
	

	public static final String PREFIX = "jwl-";
	public static final String FORM_NAME = PREFIX + "form-id";
	public static final String ELEMENT = "form";
	public static final String METHOD_POST = "post";
	public static final String METHOD_GET = "get";

	protected String action;
	protected String method;
	protected String enctype;
	protected String id;
	protected Boolean fileType = Boolean.FALSE;
	protected String name;
	protected Map<String, HtmlInputExtended> components;

	public HtmlAppForm(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Form name can't be empty or null!");
		}
		this.name = name;
		this.id = PREFIX + name;
		this.method = METHOD_POST;
		this.enctype = "application/x-www-form-urlencoded";
		this.components = new LinkedHashMap<String, HtmlInputExtended>();
	}

	public String getName() {
		return name;
	}
	
	public String getAction() {
		return action;
	}

	
	public void setAction(String action) {
		this.action = action;
	}

	
	public String getEnctype() {
		return enctype;
	}

	
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	
	public String getMethod() {
		return method;
	}

	
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(ELEMENT, this);
		writer.writeAttribute("action", this.action, null);
		
		if (this.fileType) {
			this.enctype = "multipart/form-data";
		}
		
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
			
		String validation = this.createJSValidation();
		if (validation != null) {
			HtmlScript script = new HtmlScript();
			script.setScript(validation);
			script.encodeAll(context);
		}
	}
	
	protected String createJSValidation() {
		JSValidation js = new JSValidation(this);
		return js.generate();
	}

	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		HtmlPanelGrid table = new HtmlPanelGrid();
		table.setColumns(2);
		table.setStyleClass("jwl-form");
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

	
	public HtmlInputExtended addText(String name, String label, String value) {
		HtmlInputText input = new HtmlInputText();
		input.setId(this.createName(name));
		input.setValue(value);
		input.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(input, label);
		this.addComponent(name, labeled);
		return labeled;
	}

	
	public HtmlInputExtended addFile(String name, String label) {
		this.fileType = Boolean.TRUE;
		HtmlInputFile input = new HtmlInputFile();
		input.setId(this.createName(name));
		input.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(input, label);
		this.addComponent(name, labeled);
		return labeled;
	}

	
	public HtmlInputExtended addPassword(String name, String label) {
		HtmlInputSecret input = new HtmlInputSecret();
		input.setId(this.createName(name));
		input.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(input, label);
		this.addComponent(name, labeled);
		return labeled;
	}

	
	public HtmlInputExtended addHidden(String name, String value) {
		HtmlInputHidden input = new HtmlInputHidden();
		input.setId(this.createName(name));
		input.setValue(value);
		HtmlInputExtended extended = new HtmlInputExtended(input, null);
		this.addComponent(name, extended);
		return extended;
	}

	
	public HtmlInputExtended addTextArea(String name, String label, String value) {
		HtmlInputTextarea textarea = new HtmlInputTextarea();
		textarea.setId(this.createName(name));
		textarea.setValue(value);
		textarea.setLabel(label);
		HtmlInputExtended labeled = new HtmlInputExtended(textarea, label);
		this.addComponent(name, labeled);
		return labeled;
	}

	
	public HtmlInputExtended addSubmit(String name, String caption, String label) {
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(this.createName(name));
		button.setValue(caption);
		button.setLabel(label);
		HtmlInputExtended extended = new HtmlInputExtended(button, null);
		this.addComponent(name, extended);
		return extended;
	}

	
	public HtmlInputExtended addCheckbox(String name, String caption) {
		HtmlSelectBooleanCheckbox checkbox = new HtmlSelectBooleanCheckbox();
		checkbox.setId(this.createName(name));
		checkbox.setLabel(caption);
		checkbox.setValue(false);
		HtmlInputExtended labeled = new HtmlInputExtended(checkbox, caption);
		this.addComponent(name, labeled);
		return labeled;
	}

	
	public Map<String, HtmlInputExtended> getInputs() {
		return this.components;
	}

	
	public HtmlInputExtended get(String name) {
		return this.components.get(name);
	}

	protected String createName(String name) {
		return getFormIdPostfix() + name;
	}

	protected String getFormIdPostfix() {
		return this.id + "-";
	}

	protected UIComponent createLabel(String label, String id) {
		HtmlLabel comp = new HtmlLabel();
		comp.setText(label);
		comp.setFor(id);
		return comp;
	}

	protected void addComponent(String name, HtmlInputExtended component) {
		this.components.put(name, component);
	}

	
	public void remove(String name) {
		this.components.remove(name);
	}
	
	public void process(Map<String, Object> requestMap) {
		for (String key : requestMap.keySet()) {
			if (!key.startsWith(this.getFormIdPostfix())) {
				continue;
			}

			HtmlInputExtended input = this.get(key.substring(this.getFormIdPostfix().length()));
			if (input == null) {
				continue;
			}

			Object value = requestMap.get(key);
			if (input.getComponent() instanceof HtmlSelectBooleanCheckbox) {
				value = (value.toString().equals("on") ? Boolean.TRUE : Boolean.FALSE);
			}
			input.setValue(value);
		}	
	}
}
