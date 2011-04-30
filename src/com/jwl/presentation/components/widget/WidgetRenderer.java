package com.jwl.presentation.components.widget;

import com.jwl.business.security.IIdentity;
import java.io.IOException;
import java.util.Map;

import javax.faces.component.html.HtmlOutputText;

import com.jwl.presentation.core.AbstractRenderer;
import com.jwl.presentation.html.HtmlLink;
import com.jwl.presentation.url.Linker;


public class WidgetRenderer extends AbstractRenderer {

	public WidgetRenderer(Linker linker, IIdentity identity, Map<String, Object> params) {
		super(linker, identity, params);
	}

	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("ja jsem default view");
		super.components.add(message);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.buildLink("detail"));
		link.setText("ja jsem AJAXovy odkaz na detail");
		link.setIsAjax(Boolean.TRUE);
		super.components.add(link);

		HtmlLink linkNonAjax = new HtmlLink();
		linkNonAjax.setValue(this.linker.buildLink("detail"));
		linkNonAjax.setText("ja jsem NEajaxovy odkaz na detail");
		linkNonAjax.setIsAjax(Boolean.FALSE);
		super.components.add(linkNonAjax);
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("ja jsem detail view");
		super.components.add(message);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.buildLink("default"));
		link.setText("ja jsem AJAXovy odkaz na default");
		super.components.add(link);
	}

}
