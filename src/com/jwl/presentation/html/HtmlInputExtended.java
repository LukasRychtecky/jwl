package com.jwl.presentation.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlInputExtended extends UIInput {

	protected String label;
	protected UIComponent component;

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

}
