package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.HistoryId;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IRestoreArticleUC {

	public void restore(HistoryId id) throws ModelException;

}
