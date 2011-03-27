package com.jwl.presentation.core;

import com.jwl.presentation.global.WikiURLParser;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Router {

	private WikiURLParser parser;

	public Router(FacesContext context) {
		this.parser = new WikiURLParser(context);
	}

	public void route(Presenter presenter) throws IOException {
		String action = this.parser.getAction();
		if (action == null || action.isEmpty()) {
			presenter.renderDefault();
		} else {

			String methodName = "render" + action.substring(0, 1).toUpperCase().concat(action.substring(1));
			try {
				Method method = presenter.getClass().getMethod(methodName);
				method.invoke(presenter);
			} catch (IllegalAccessException ex) {
				this.logException(new RouteException("Method " + presenter.getClass().toString() + "." + methodName + " must be declarated as public.", ex));
				presenter.render404();
			} catch (IllegalArgumentException ex) {
				this.logException(ex);
				presenter.render500();
			} catch (InvocationTargetException ex) {
				this.logException(ex);
				presenter.render500();
			} catch (NoSuchMethodException ex) {
				this.logException(new RouteException("No such method found " + presenter.getClass().toString() + "." + methodName, ex));
				presenter.render404();
			} catch (SecurityException ex) {
				this.logException(ex);
				presenter.render500();
			}
		}
	}

	protected void logException(Exception e) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	}

}
