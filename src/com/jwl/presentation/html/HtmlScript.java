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
	
	protected String type = "text/javascript";
	protected String src;
	protected String script;

	public void setSrc(String src) {
		this.src = src;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(this.getElement(), this);
		writer.writeAttribute("type", this.type, null);
		if (this.src != null) {
			writer.writeAttribute("src", this.src, null);
		}
		if (this.script != null) {
			writer.write(this.script);
		}
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException{
	}
	
	@Override
	public String getElement() {
		return "script";
	}
	
	
}
