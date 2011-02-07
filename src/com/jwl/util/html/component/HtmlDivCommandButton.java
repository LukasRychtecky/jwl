package com.jwl.util.html.component;

import javax.faces.component.html.HtmlCommandButton;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlDivCommandButton extends HtmlCommandButton {

	private String divStyleClass;
	
	public HtmlDivCommandButton() {
		super();
		setRendererType(RendererTypes.DIV_COMMAND_BUTTON_RENDERER);
	}

	public String getDivStyleClass() {
		return divStyleClass;
	}

	public void setDivStyleClass(String divStyleClass) {
		this.divStyleClass = divStyleClass;
	}

}
