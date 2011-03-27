package com.jwl.util.html.component;

import javax.faces.component.html.HtmlOutputText;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlFreeOutput extends HtmlOutputText {

	private String freeOutput;

	public HtmlFreeOutput() {
		super();
		setRendererType(RendererTypes.FREE_OUTPUT_RENDERER);
	}

	public String getFreeOutput() {
		return freeOutput;
	}

	public void setFreeOutput(String freeOutput) {
		this.freeOutput = freeOutput;
	}

}
