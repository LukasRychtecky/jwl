package com.jwl.util.html.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jwl.util.html.component.HtmlFreeOutput;
import com.sun.faces.renderkit.html_basic.TextRenderer;

public class FreeOutputRenderer extends TextRenderer {
	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		HtmlFreeOutput myComponent = (HtmlFreeOutput) component;
		encodeChildren(context, myComponent);
		if (myComponent.getFreeOutput() != null
				&& myComponent.getFreeOutput() != "") {
			writer.write(myComponent.getFreeOutput());
		}
		for(UIComponent child : myComponent.getChildren()){
			child.encodeAll(context);
		}
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
	}
}
