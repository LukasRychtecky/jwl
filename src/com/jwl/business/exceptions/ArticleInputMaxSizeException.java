package com.jwl.business.exceptions;

public class ArticleInputMaxSizeException extends BusinessProcessException {

	private static final long serialVersionUID = 6939482677473153916L;

	public ArticleInputMaxSizeException(Throwable thrwbl) {
		super(thrwbl);
	}

	public ArticleInputMaxSizeException(String string) {
		super(string);
	}

	public ArticleInputMaxSizeException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

}
