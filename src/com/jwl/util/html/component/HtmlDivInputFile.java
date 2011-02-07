package com.jwl.util.html.component;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlDivInputFile extends HtmlInputFile {
	
	private String divStyleClass;

	public HtmlDivInputFile() {
		super();
		setRendererType(RendererTypes.DIV_INPUT_FILE_RENDERER);
	}
	
	public String getDivStyleClass() {
		return divStyleClass;
	}

	public void setDivStyleClass(String divStyleClass) {
		this.divStyleClass = divStyleClass;
	}

}
