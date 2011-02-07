package com.jwl.business.exceptions;

public class ArticleExistsException extends BusinessProcessException {

	private static final long serialVersionUID = 8874778673443375152L;

	public ArticleExistsException(Throwable thrwbl) {
		super(thrwbl);
	}

	public ArticleExistsException(String string) {
		super(string);
	}

	public ArticleExistsException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

}
