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
		this.renderer = new WidgetRenderer(this.context);
	}

	@Override
	public void renderDefault() {
		try {
			this.renderer.renderDefault();
		} catch (IOException e) {
			this.logException(e);
		}
	}

	public void renderDetail() {
		try {
			this.renderer.renderDetail();
		} catch (IOException e) {
			this.logException(e);
		}
	}
}
