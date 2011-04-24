package com.jwl.presentation.core;

/**
 *
 * @author Lukas Rychtecky
 */
public class RouteException extends Exception {

	private static final long serialVersionUID = 8874778673443375152L;

	public RouteException(Throwable thrwbl) {
		super(thrwbl);
	}

	public RouteException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public RouteException(String string) {
		super(string);
	}

}
