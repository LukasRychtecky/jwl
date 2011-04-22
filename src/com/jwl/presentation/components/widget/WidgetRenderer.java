package com.jwl.presentation.components.widget;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import com.jwl.presentation.components.core.AbstractRenderer;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;


public class WidgetRenderer extends AbstractRenderer {

	public WidgetRenderer(FacesContext context, Linker linker, List<UIComponent> components) {
		super(context, linker, components);
	}

	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("ja jsem default view");
		super.components.add(message);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.buildState("detail"));
		link.setText("ja jsem AJAXovy odkaz na detail");
		link.setIsAjax(Boolean.TRUE);
		super.components.add(link);

		HtmlLink linkNonAjax = new HtmlLink();
		linkNonAjax.setValue(this.linker.buildState("detail"));
		linkNonAjax.setText("ja jsem NEajaxovy odkaz na detail");
		linkNonAjax.setIsAjax(Boolean.FALSE);
		super.components.add(linkNonAjax);
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("ja jsem detail view");
		super.components.add(message);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.buildState("default"));
		link.setText("ja jsem AJAXovy odkaz na default");
		super.components.add(link);
	}

}
