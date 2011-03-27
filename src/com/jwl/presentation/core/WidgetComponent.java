package com.jwl.presentation.core;

import com.jwl.presentation.component.controller.JWLComponent;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.presentation.presenters.widget.WidgetPresenter;
import com.jwl.util.html.url.URLParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		Presenter presenter = new WidgetPresenter(context);
		WikiURLParser parser = new WikiURLParser();
		String action = parser.getAction();
		if (action == null || action.isEmpty()) {
			presenter.renderDefault();
		} else {

			action = action.substring(0, 1).toUpperCase().concat(action.substring(1));
			try {
				Method method = presenter.getClass().getMethod("render" + action);
				method.invoke(presenter);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(WidgetComponent.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(WidgetComponent.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InvocationTargetException ex) {
				Logger.getLogger(WidgetComponent.class.getName()).log(Level.SEVERE, null, ex);
			} catch (NoSuchMethodException ex) {
				Logger.getLogger(WidgetComponent.class.getName()).log(Level.SEVERE, null, ex);
			} catch (SecurityException ex) {
				Logger.getLogger(WidgetComponent.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
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
