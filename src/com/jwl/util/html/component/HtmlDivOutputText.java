package com.jwl.util.html.component;

import javax.faces.component.html.HtmlOutputText;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlDivOutputText extends HtmlOutputText {
	
	private String divStyleClass;

	public HtmlDivOutputText() {
		super();
		setRendererType(RendererTypes.DIV_OUTPUT_TEXT_RENDERER);
	}
	
	public String getDivStyleClass() {
		return divStyleClass;
	}

	public void setDivStyleClass(String divStyleClass) {
		this.divStyleClass = divStyleClass;
	}

}
