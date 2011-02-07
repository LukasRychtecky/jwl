package com.jwl.util.html.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jwl.util.html.component.HtmlDivInputText;
import com.sun.faces.renderkit.html_basic.TextRenderer;

public class DivInputTextRenderer extends TextRenderer {

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
		if (isDivStyleClassSet(component)) {
			ResponseWriter responseWriter = context.getResponseWriter();
			responseWriter.startElement("div", component);
			responseWriter.writeAttribute("class", getDivStyleClass(component),
					null);
		}
	}

	protected void encodeDivEnd(FacesContext context, UIComponent component)
			throws IOException {
		if (isDivStyleClassSet(component)) {
			context.getResponseWriter().write("</div>");
		}
	}

	private boolean isDivStyleClassSet(UIComponent component) {
		if (component instanceof HtmlDivInputText) {
			HtmlDivInputText divInputText = (HtmlDivInputText) component;
			String styleClass = divInputText.getDivStyleClass();
			return (null != styleClass && styleClass.length() > 0);
		}
		return false;
	}

	private String getDivStyleClass(UIComponent component) {
		return ((HtmlDivInputText) component).getDivStyleClass();
	}
}
