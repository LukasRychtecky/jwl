package com.jwl.presentation.html.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jwl.presentation.html.HtmlActionForm;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.config.WebConfiguration.BooleanWebContextInitParameter;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.FormRenderer;

public class ActionFormRenderer extends FormRenderer {

	private static final Attribute[] ATTRIBUTES = AttributeManager
			.getAttributes(AttributeManager.Key.FORMFORM);
	private boolean writeStateAtEnd;

	public ActionFormRenderer() {
		WebConfiguration webConfig = WebConfiguration.getInstance();
		writeStateAtEnd = webConfig
				.isOptionEnabled(BooleanWebContextInitParameter.WriteStateAtFormEnd);

	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}

		ResponseWriter writer = context.getResponseWriter();
		assert (writer != null);
		String clientId = component.getClientId(context);
		writer.write('\n');
		writer.startElement("form", component);
		writer.writeAttribute("id", clientId, "clientId");
		writer.writeAttribute("name", clientId, "name");
		writer.writeAttribute("method", "post", null);
		writer.writeAttribute("action", getActionStr(context, component), null);
		String styleClass = (String) component.getAttributes()
				.get("styleClass");
		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, "styleClass");
		}
		String acceptcharset = (String) component.getAttributes().get(
				"acceptcharset");
		if (acceptcharset != null) {
			writer.writeAttribute("accept-charset", acceptcharset,
					"acceptcharset");
		}

		RenderKitUtils.renderPassThruAttributes(context, writer, component,
				ATTRIBUTES);
		writer.writeText("\n", component, null);

		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", "type");
		writer.writeAttribute("name", clientId, "clientId");
		writer.writeAttribute("value", clientId, "value");
		writer.endElement("input");
		writer.write('\n');

		String viewId = context.getViewRoot().getViewId();
		String actionURL = context.getApplication().getViewHandler()
				.getActionURL(context, viewId);
		ExternalContext externalContext = context.getExternalContext();
		String encodedActionURL = externalContext.encodeActionURL(actionURL);
		String encodedPartialActionURL = externalContext
				.encodePartialActionURL(actionURL);
		if (encodedPartialActionURL != null) {
			if (!encodedPartialActionURL.equals(encodedActionURL)) {
				writer.startElement("input", component);
				writer.writeAttribute("type", "hidden", "type");
				writer.writeAttribute("name", "javax.faces.encodedURL", null);
				writer
						.writeAttribute("value", encodedPartialActionURL,
								"value");
				writer.endElement("input");
				writer.write('\n');
			}
		}

		if (!writeStateAtEnd) {
			context.getApplication().getViewHandler().writeState(context);
			writer.write('\n');
		}
	}

	private String getActionStr(FacesContext context, UIComponent component) {
		String actionURL = null;

		if (component instanceof HtmlActionForm){
			HtmlActionForm actionForm = (HtmlActionForm) component;
			actionURL = actionForm.getAction();
		} 
		if (null == actionURL || actionURL.length() == 0) {
			String viewId = context.getViewRoot().getViewId();
			actionURL = context.getApplication().getViewHandler()
				.getActionURL(context, viewId);
		}
		
		return (context.getExternalContext().encodeActionURL(actionURL));

	}
}
