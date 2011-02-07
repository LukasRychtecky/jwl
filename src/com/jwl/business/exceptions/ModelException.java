package com.jwl.business.exceptions;

/**
 * Base JWL model exception.
 *
 * @author Lukas Rychtecky
 */
public class ModelException extends Exception {

	private static final long serialVersionUID = -4751480580025983730L;
	
	public ModelException(Throwable thrwbl) {
		super(thrwbl);
	}

	public ModelException(String string) {
		super(string); 
	}

	public ModelException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

}
