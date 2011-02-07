package com.jwl.integration.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class DuplicateEntryException extends DAOException {

	private static final long serialVersionUID = 6939482677473153916L;

	public DuplicateEntryException(String string) {
		super(string);
	}

	public DuplicateEntryException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public DuplicateEntryException(Throwable thrwbl) {
		super(thrwbl);
	}

}
