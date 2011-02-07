package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class NoAuthenticatedYetException extends ModelException {

	private static final long serialVersionUID = -4751480580025983730L;

	public NoAuthenticatedYetException(String string) {
		super(string);
	}

	public NoAuthenticatedYetException(Throwable thrwbl) {
		super(thrwbl);
	}

}
