package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IGetHistoriesUC {

	public List<HistoryTO> get(ArticleId id) throws ModelException;

}
