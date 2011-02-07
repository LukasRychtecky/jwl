package com.jwl.business.article.usecases;

import com.jwl.business.exceptions.ArticleInputMaxSizeException;
import com.jwl.business.exceptions.InsufficientArticleDataException;

/**
 * 
 * @author
 * @review Petr Dytrych, Jiri Ostatnicky
 */
public abstract class AbstractUC {

	public AbstractUC() {
	}

	/**
	 * Check if it is set. If don't, throws exception.
	 * 
	 * @param data checked object
	 * @param error error text
	 * @throws InsufficientArticleDataException
	 */
	protected void checkIsSet(Object data, String error)
			throws InsufficientArticleDataException {
		
		if (data == null) {
			throw new InsufficientArticleDataException(error);
		}
	}

	/**
	 * Check if text length is not bigger than requirement. If don't, throws
	 * exception.
	 * 
	 * @param text input text
	 * @param maxLength max length
	 * @param error error text
	 * @throws ArticleInputMaxSizeException
	 */
	protected void checkMaxSizeText(String text, int maxLength, String error)
			throws ArticleInputMaxSizeException {
		if (text.length() > maxLength) {
			throw new ArticleInputMaxSizeException(error);
		}
	}

}
