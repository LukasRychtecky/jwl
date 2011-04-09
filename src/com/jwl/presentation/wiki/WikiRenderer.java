package com.jwl.presentation.wiki;

import com.jwl.presentation.core.Linker;
import java.io.IOException;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WikiRenderer extends com.jwl.presentation.core.Renderer {

	public WikiRenderer(FacesContext context, Linker linker) {
		super(context, linker);
	}

	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Ahoj ja jsem nova komponenta");
		message.encodeAll(this.context);

		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.build("detail"));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na detail");
		link.getChildren().add(text);
		link.encodeAll(context);
	}

	public void renderDetail() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("Renderujeme detail!");
		message.encodeAll(this.context);

		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.build("default"));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na default");
		link.getChildren().add(text);
		link.encodeAll(context);
	}

}
