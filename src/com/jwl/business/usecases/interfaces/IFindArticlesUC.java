package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.exceptions.ModelException;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IFindArticlesUC {

	public List<ArticleTO> find(SearchTO search) throws ModelException;

}
