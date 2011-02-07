package com.jwl.integration.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class EntityNotFoundException extends DAOException {

	private static final long serialVersionUID = 6939482677473153916L;

	public EntityNotFoundException(String string) {
		super(string);
	}

	public EntityNotFoundException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public EntityNotFoundException(Throwable thrwbl) {
		super(thrwbl);
	}
	

}
