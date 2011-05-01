package com.jwl.presentation.html;

import java.io.IOException;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Lukas Rychtecky
 */
public final class HtmlLabel extends UIOutput {

	public static final String ELEMENT = "label";
	protected String forAttr;
	protected String text;

	public HtmlLabel() {
		super();
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(ELEMENT, this);
		writer.writeAttribute("for", this.forAttr, null);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.write(this.text);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		this.getWriter(context).endElement(ELEMENT);
	}

	private ResponseWriter getWriter(FacesContext context) throws IOException {
		return context.getResponseWriter();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFor() {
		return forAttr;
	}

	public void setFor(String forAttr) {
		this.forAttr = forAttr;
	}
}
