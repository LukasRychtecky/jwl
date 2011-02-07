package com.jwl.integration.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 6939482677473153916L;

	public DAOException(Throwable thrwbl) {
		super(thrwbl);
	}

	public DAOException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public DAOException(String string) {
		super(string);
	}

}
