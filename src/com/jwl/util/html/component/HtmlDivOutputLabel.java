package com.jwl.util.html.component;

import javax.faces.component.html.HtmlOutputLabel;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlDivOutputLabel extends HtmlOutputLabel {

	private String divStyleClass;

	public HtmlDivOutputLabel() {
		super();
		setRendererType(RendererTypes.DIV_OUTPUT_LABEL_RENDERER);
	}

	public String getDivStyleClass() {
		return divStyleClass;
	}

	public void setDivStyleClass(String divStyleClass) {
		this.divStyleClass = divStyleClass;
	}
}
