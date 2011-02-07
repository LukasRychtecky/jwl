package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class ObjectNotFoundException extends ModelException {

	private static final long serialVersionUID = -4751480580025983730L;

	public ObjectNotFoundException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public ObjectNotFoundException(String string) {
		super(string);
	}

	public ObjectNotFoundException(Throwable thrwbl) {
		super(thrwbl);
	}

}
