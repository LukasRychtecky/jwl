package com.jwl.presentation.html;

import com.jwl.presentation.forms.Rule;
import com.jwl.presentation.forms.Validation;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlInputExtended extends UIInput {

	protected String label;
	protected UIComponent component;
	protected Rule rule;

	public HtmlInputExtended(UIComponent component, String label) {
		this.label = label;
		this.component = component;
	}

	public String getLabel() {
		return label;
	}

	public UIComponent getComponent() {
		return component;
	}
	
	public void setStyleClass(String styleClass) {
		if (component instanceof HtmlInputText) {
			HtmlInputText c = (HtmlInputText) component;
			c.setStyleClass(styleClass);
		} else if (component instanceof HtmlInputTextarea) {
			HtmlInputTextarea c = (HtmlInputTextarea) component;
			c.setStyleClass(styleClass);
		} else if (component instanceof HtmlInputFile) {
			HtmlInputFile c = (HtmlInputFile) component;
			c.setStyleClass(styleClass);
		} else if (component instanceof HtmlInputSecret) {
			HtmlInputSecret c = (HtmlInputSecret) component;
			c.setStyleClass(styleClass);
		} else if (component instanceof HtmlCommandButton) {
			HtmlCommandButton c = (HtmlCommandButton) component;
			c.setStyleClass(styleClass);
		} else if (component instanceof HtmlSelectBooleanCheckbox) {
			HtmlSelectBooleanCheckbox c = (HtmlSelectBooleanCheckbox) component;
			c.setStyleClass(styleClass);
		}
	}

	@Override
	public Object getValue() {
		Object value = null;
		if (this.component instanceof UIInput) {
			UIInput input = (UIInput) this.component;
			value = input.getValue();
		}
		return value;
	}

	@Override
	public void setValue(Object value) {
		if (this.component instanceof UIInput) {
			UIInput input = (UIInput) this.component;
			input.setValue(value);
		}
	}
	
	public Rule getRule() {
		return this.rule;
	}
	
	public HtmlInputExtended addRule(Validation type, String message, List<?> args) {
		this.rule = new Rule(type, message, args);
		return this;
	}
	
	public HtmlInputExtended addRule(Validation type, String message) {
		return this.addRule(type, message, new ArrayList());
	}

}
