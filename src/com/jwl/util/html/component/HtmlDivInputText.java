package com.jwl.util.html.component;

import javax.faces.component.html.HtmlInputText;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlDivInputText extends HtmlInputText {

	private String divStyleClass;

	public HtmlDivInputText() {
		super();
		setRendererType(RendererTypes.DIV_INPUT_TEXT_RENDERER);
	}
	
	public String getDivStyleClass() {
		return divStyleClass;
	}

	public void setDivStyleClass(String divStyleClass) {
		this.divStyleClass = divStyleClass;
	}
}
