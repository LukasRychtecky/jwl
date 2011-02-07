package com.jwl.util.html.renderer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jwl.util.html.component.HtmlHeaderPanelGrid;
import com.sun.faces.renderkit.html_basic.GridRenderer;

public class HeaderPanelGridRenderer extends GridRenderer {

	@Override
	protected void renderHeader(FacesContext context, UIComponent component,
			ResponseWriter writer) throws IOException {
		
		String headerClass = (String) component.getAttributes().get("headerClass");

		writer.startElement("thead", component);
		if (headerClass != null) {
			writer.writeAttribute("class", headerClass, "headerClass");
		}
		writer.write('\n');
		writer.startElement("tr", null);
		writer.write('\n');
		
		for (String header : getHeaders(component)) {
			writer.startElement("th", null);
			writer.write(header);
			writer.endElement("th");
			writer.write('\n');
		}
		
		writer.endElement("tr");
		writer.write('\n');
		writer.endElement("thead");
		writer.write('\n');
	}

	private List<String> getHeaders(UIComponent component) {
		if(component instanceof HtmlHeaderPanelGrid){
			return ((HtmlHeaderPanelGrid) component).getHeaders();
		} else {
			return Collections.emptyList();
		}
	}


}
