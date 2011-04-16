package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.core.Linker;
import com.jwl.presentation.html.HtmlLink;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Renderer extends com.jwl.presentation.core.AbstractRenderer {

	public Renderer(FacesContext context, Linker linker, List<UIComponent> components) {
		super(context, linker, components);
	}

	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("ja jsem default view");
		super.components.add(message);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.build("detail"));
		link.setText("ja jsem AJAXovy odkaz na detail");
		super.components.add(link);

		HtmlLink linkNonAjax = new HtmlLink();
		linkNonAjax.setValue(this.linker.build("detail"));
		linkNonAjax.setIsAjax(Boolean.FALSE);
		linkNonAjax.setText("ja jsem NEajaxovy odkaz na detail");
		super.components.add(linkNonAjax);
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("ja jsem detail view");
		super.components.add(message);

		HtmlLink link = new HtmlLink();
		link.setValue(this.linker.build("default"));
		link.setText("ja jsem AJAXovy odkaz na default");
		super.components.add(link);
	}

}
