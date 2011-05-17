package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class InvalidFileFormatException extends ModelException {
	private static final long serialVersionUID = 1L;

	public InvalidFileFormatException(String string) {
		super(string);
	}
	
}
