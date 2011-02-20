package com.jwl.business.article.usecases;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ArticleInputMaxSizeException;
import com.jwl.business.exceptions.InsufficientArticleDataException;
import com.jwl.integration.article.IArticleDAO;

/**
 * 
 * @review Petr Dytrych, Jiri Ostatnicky
 */
public abstract class AbstractArticleUC extends AbstractUC {

	protected IArticleDAO dao;

	protected static final int TITLE_MAX_SIZE = 255;
	protected static final int EDITOR_MAX_SIZE = 100;
	protected static final int CHANGE_NOTE_MAX_SIZE = 255;

	public AbstractArticleUC(IArticleDAO dao) {
		this.dao = dao;
	}

	/**
	 * This method check main article parameters that are essential to business
	 * process. If there is something wrong, throws exception.
	 * 
	 * @param articleTO article transfer object
	 * @throws InsufficientArticleDataException
	 * @throws ArticleInputMaxSizeException
	 */
	protected void assertArticleValidInput(ArticleTO articleTO)
			throws InsufficientArticleDataException,
			ArticleInputMaxSizeException {

		// Set Checking
		checkIsSet(articleTO, "Article is not set");
		checkIsSet(articleTO.getTitle(), "Article tile is not set");
		checkIsSet(articleTO.getEditor(), "Article editor is not set");

		// Checking of max length
		checkMaxSizeText(articleTO.getTitle(), TITLE_MAX_SIZE,
				"Max size of article title is " + TITLE_MAX_SIZE);
		checkMaxSizeText(articleTO.getEditor(), EDITOR_MAX_SIZE,
				"Max size of article editor is " + EDITOR_MAX_SIZE);
		checkMaxSizeText(articleTO.getChangeNote(), CHANGE_NOTE_MAX_SIZE,
				"Max size of article change note is " + CHANGE_NOTE_MAX_SIZE);
	}

	/**
	 * If input title exists, throw exception. Else nothing.
	 * 
	 * @param title
	 * @throws ArticleExistsException
	 */
//	protected void assertTitleNotExist(String title)
//			throws ArticleExistsException {
//		if (this.dao.getByTitle(title) != null) {
//			throw new ArticleExistsException("An article with title '" + title
//					+ "' exists.");
//		}
//	}
}
