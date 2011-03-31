package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.core.Linker;
import com.jwl.util.html.component.HtmlDivCommandButton;
import java.io.IOException;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Renderer extends com.jwl.presentation.core.Renderer {

	public Renderer(FacesContext context, Linker linker) {
		super(context, linker);
	}

	@Override
	public void renderDefault() throws IOException {
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("AHoj ja jsem nova komponenta");
		message.encodeAll(this.context);

		HtmlOutputLink link = new HtmlOutputLink();
		link.setValue(this.linker.build("detail"));

		HtmlOutputText text = new HtmlOutputText();
		text.setValue("odkaz na detail");
		link.getChildren().add(text);
		link.encodeAll(context);

		HtmlDivCommandButton submit = new HtmlDivCommandButton();
		submit.setType("submit");
		submit.setId("ajax");
		submit.setValue("Click");
		submit.encodeAll(context);
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
