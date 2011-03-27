package com.jwl.presentation.core;

import com.jwl.presentation.component.controller.JWLComponent;
import com.jwl.presentation.presenters.widget.WidgetPresenter;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class WidgetComponent extends JWLComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Widget";
	public static final String DEFAULT_RENDERER = "com.jwl.component.WidgetRenderer";

	public WidgetComponent() {
		super();
//		this.setRendererType(DEFAULT_RENDERER);
	}

	private void route(FacesContext context) {
		Router router = new Router(context);
		router.route(new WidgetPresenter(context));
	}

	@Override
	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
		values[0] = super.saveState(context);
		return values;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
	}

	@Override
	public void encodeAll(FacesContext context) {
		this.route(context);
	}
}
