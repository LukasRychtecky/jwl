package com.jwl.business.exceptions;

public class InsufficientArticleDataException extends BusinessProcessException {


	private static final long serialVersionUID = 6902727972533680874L;

	public InsufficientArticleDataException(String string) {
		super(string);
	}
	
	public InsufficientArticleDataException(Throwable thrwbl) {
		super(thrwbl);
	}
	
}
