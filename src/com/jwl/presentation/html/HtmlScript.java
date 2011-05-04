package com.jwl.presentation.html;

import java.io.IOException;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Lukas Rychtecky
 */
public final class HtmlScript extends HtmlElement {
	
	protected String type;
	protected String src = "text/javascript";

	public void setSrc(String src) {
		this.src = src;
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(this.getElement(), this);
		writer.writeAttribute("type", this.type, null);
		writer.writeAttribute("src", this.src, null);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException{
	}
	
	@Override
	public String getElement() {
		return "script";
	}
	
	
}
