package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class NoRoleFoundException extends ModelException {

	private static final long serialVersionUID = -4751480580025983730L;

	public NoRoleFoundException(String string) {
		super(string);
	}

	public NoRoleFoundException(Throwable thrwbl) {
		super(thrwbl);
	}
}
