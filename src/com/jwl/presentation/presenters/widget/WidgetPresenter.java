package com.jwl.presentation.presenters.widget;

import com.jwl.presentation.core.Presenter;
import java.io.IOException;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetPresenter extends Presenter {

	private WidgetRenderer renderer;

	public WidgetPresenter(FacesContext context) {
		super(context);
		this.renderer = new WidgetRenderer(this.context, super.linker);
	}

	@Override
	public void renderDefault() throws IOException {
		this.renderer.renderDefault();
	}

	public void renderDetail() throws IOException {
		this.renderer.renderDetail();
	}
}
