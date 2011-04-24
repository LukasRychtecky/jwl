package com.jwl.presentation.core;

import com.jwl.presentation.enumerations.JWLContextKey;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jwl.presentation.global.ExceptionLogger;
import com.jwl.presentation.url.WikiURLParser;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Router {

	private WikiURLParser parser;

	public Router() {
		this.parser = new WikiURLParser();
	}

	public void route(AbstractPresenter presenter) throws IOException {
		String doAction = this.parser.getDoAction();
		FacesContext.getCurrentInstance().getAttributes().put(JWLContextKey.STATE, this.parser.getState());
		
		String methodName = null;
		
		if (doAction != null && !doAction.isEmpty()) {
			methodName = "decode" + doAction.substring(0, 1).toUpperCase().concat(doAction.substring(1));
			invokeMethod(presenter, methodName);
		}
		
		Object stateAttr = FacesContext.getCurrentInstance().getAttributes().get(JWLContextKey.STATE);
		String state = 	(stateAttr != null ? stateAttr.toString() : "");
		if (!state.isEmpty()) {
			methodName = "render" + state.substring(0, 1).toUpperCase().concat(state.substring(1));
		} else {
			methodName = "renderDefault";
		}
		
		invokeMethod(presenter, methodName);
		presenter.sendResponse();
	}

	private boolean invokeMethod(AbstractPresenter presenter, String methodName) 
		throws IOException {
		
		try {
			Class<? extends AbstractPresenter> clazz = presenter.getClass();
			Method method = clazz.getMethod(methodName);
			method.invoke(presenter);
			return true;
		} catch (IllegalAccessException ex) {
			RouteException routeException = new RouteException(
					"Method " + presenter.getClass().toString() + "." + 
					methodName + " must be declarated as public.", ex);
			ExceptionLogger.severe(getClass(), routeException);
			presenter.render404();
		} catch (IllegalArgumentException ex) {
			ExceptionLogger.severe(getClass(), ex);
			presenter.render500();
		} catch (InvocationTargetException ex) {
			ExceptionLogger.severe(getClass(), ex);
			presenter.render500();
		} catch (NoSuchMethodException ex) {
			RouteException routeException = new RouteException(
					"No such method found " + presenter.getClass().toString() + 
					"." + methodName, ex);
			ExceptionLogger.severe(getClass(), routeException);
			presenter.render404();
		} catch (SecurityException ex) {
			ExceptionLogger.severe(getClass(), ex);
			presenter.render500();
		} catch (Exception ex) {
			ExceptionLogger.severe(getClass(), ex);
			presenter.render500();
		}
		return false;
	}

}
