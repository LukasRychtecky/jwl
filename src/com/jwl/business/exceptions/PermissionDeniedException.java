package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class PermissionDeniedException extends ModelException {

	private static final long serialVersionUID = -4751480580025983730L;

	public PermissionDeniedException(String string) {
		super(string);
	}

	public PermissionDeniedException(Throwable thrwbl) {
		super(thrwbl);
	}


}
