package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class UnexpectedActionException extends ModelException {

	private static final long serialVersionUID = -4751480580025983730L;

	public UnexpectedActionException(String string) {
		super(string);
	}

	public UnexpectedActionException(Throwable thrwbl) {
		super(thrwbl);
	}

	

}
