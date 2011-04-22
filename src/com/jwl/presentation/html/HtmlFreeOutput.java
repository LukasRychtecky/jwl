package com.jwl.presentation.html;

import java.io.IOException;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class HtmlFreeOutput extends UIOutput {

	private String freeOutput;

	public HtmlFreeOutput() {
		super();
	}

	public String getFreeOutput() {
		return freeOutput;
	}

	public void setFreeOutput(String freeOutput) {
		this.freeOutput = freeOutput;
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.write(freeOutput);
	}

	private ResponseWriter getWriter(FacesContext context) throws IOException {
		return context.getResponseWriter();
	}

}
