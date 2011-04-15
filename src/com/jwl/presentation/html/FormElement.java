package com.jwl.presentation.html;

import javax.faces.component.UIComponent;

/**
 *
 * @author Lukas Rychtecky
 */
public class FormElement {
	
	protected UIComponent component;
	protected String label;

	public FormElement(UIComponent component, String label) {
		this.component = component;
		this.label = label;
	}

	public UIComponent getComponent() {
		return component;
	}

	public String getLabel() {
		return label;
	}

}
