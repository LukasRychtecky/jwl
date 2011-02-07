package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IGetHistoryUC {

	public HistoryTO get(HistoryId id) throws ModelException;

}
