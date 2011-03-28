package com.jwl.util.html.renderer;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jwl.util.html.component.HtmlDiv;
import com.sun.faces.renderkit.html_basic.TextRenderer;

public class DivRenderer extends TextRenderer {
	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		encodeDivStart(context, component);
		super.encodeBegin(context, component);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		super.encodeEnd(context, component);
		encodeDivEnd(context, component);
	}

	protected void encodeDivStart(FacesContext context, UIComponent component)
			throws IOException {
		if (!(component instanceof HtmlDiv)) {
			return;
		}
		HtmlDiv div = (HtmlDiv) component;
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", div);
		if(div.getId()!=null){
			responseWriter.writeAttribute("class", div.getId(), null);
		}
		if (!div.getStyleClasses().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = div.getStyleClasses().iterator();
			while (it.hasNext()) {
				sb.append(it.next());
				if (it.hasNext()) {
					sb.append(" ");
				}

			}
			responseWriter.writeAttribute("class", sb.toString(), null);
		}
		for(UIComponent child:div.getChildren()){
			child.encodeAll(context);
		}
	}

	protected void encodeDivEnd(FacesContext context, UIComponent component)
			throws IOException {
		if (component instanceof HtmlDiv) {
			context.getResponseWriter().endElement("div");
		}
	}

}
