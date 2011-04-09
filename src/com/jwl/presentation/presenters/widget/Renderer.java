package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.core.Linker;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLink;
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
		message.setValue("AHoj ja jsem nova komponenta");
		super.components.add(message);

		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.build("detail"));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na detail");
		link.getChildren().add(text);
		super.components.add(link);
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Renderujeme detail!");
		super.components.add(message);

		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.build("default"));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na default");
		link.getChildren().add(text);
		super.components.add(link);
	}

}
