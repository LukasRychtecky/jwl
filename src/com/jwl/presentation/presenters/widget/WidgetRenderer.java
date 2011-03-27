package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.core.Renderer;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.component.HtmlLinkProperties;
import com.jwl.util.html.component.HtmlOutputPropertyLink;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlMessage;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetRenderer extends Renderer {

	public WidgetRenderer(FacesContext context) {
		super(context);
	}

	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("AHoj ja jsem nova komponenta");
		message.encodeAll(this.context);

		HtmlLinkProperties properties = new HtmlLinkProperties();
		properties.addParameter(JWLURLParameters.ACTION, "detail");
		properties.setValue("go to detail");
		WikiURLParser parser = new WikiURLParser();
		properties.setHref(parser.getCurrentURL());
		properties.addParameters(parser
				.getURLParametersMinusArticleParameters());
		HtmlOutputLink link = new HtmlOutputPropertyLink(properties);
		link.encodeAll(this.context);
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Renderujeme detail!");
		message.encodeAll(this.context);
	}

}
