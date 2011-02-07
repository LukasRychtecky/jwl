package com.jwl.business.exceptions;

public class BusinessProcessException extends ModelException {

	private static final long serialVersionUID = -2908053697197047794L;

	public BusinessProcessException(Throwable thrwbl) {
		super(thrwbl);
	}
	
	public BusinessProcessException(String string) {
		super(string);
	}
	
	public BusinessProcessException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}
	
}
