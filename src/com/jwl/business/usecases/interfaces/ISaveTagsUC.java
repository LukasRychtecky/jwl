package com.jwl.business.usecases.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface ISaveTagsUC {

	public void save(Set<String> givenTags, ArticleId id) throws ModelException;

}
