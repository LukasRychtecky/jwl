package com.jwl.presentation.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlContainer extends UIOutput {

	public static final String ELEMENT = "div";

	private List<String> styleClasses;

	public HtmlContainer() {
		super();
		this.styleClasses = new ArrayList<String>();
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(ELEMENT, this);
		if (!this.styleClasses.isEmpty()) {
			StringBuilder styleClass = new StringBuilder();
			for (String className : this.styleClasses) {
				styleClass.append(className).append(" ");
			}
			writer.writeAttribute("class", styleClass.substring(0, styleClass.length() - 1), null);
		}
		super.encodeBegin(context);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		for (UIComponent child : super.getChildren()) {
			child.encodeAll(context);
		}
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
		ResponseWriter writer = this.getWriter(context);
		writer.endElement(ELEMENT);
	}

	public List<String> getStyleClasses() {
		return this.styleClasses;
	}

	public void setStyleClasses(List<String> styleClasses) {
		this.styleClasses = styleClasses;
	}

	private ResponseWriter getWriter(FacesContext context) throws IOException {
		return context.getResponseWriter();
	}



}
