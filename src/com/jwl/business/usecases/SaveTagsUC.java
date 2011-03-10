package com.jwl.business.usecases;

import java.util.HashSet;
import java.util.Set;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class SaveTagsUC extends AbstractUC implements ISaveTagsUC {

	public SaveTagsUC(IDAOFactory factory) {
		super(factory);
	}


	@Override
	public void save(Set<String> givenTags, ArticleId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_EDIT);
		try {
			Set<String> alreadyExistTags = super.factory.getTagDAO().getAllWhere(givenTags);
			Set<String> tagsToCreate = new HashSet<String>();
			for (String tag : givenTags) {
				if (!alreadyExistTags.contains(tag)) {
					tagsToCreate.add(tag);
				}
			}

			super.factory.getTagDAO().create(tagsToCreate, id);
			super.factory.getTagDAO().addExistingToArticle(givenTags, id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
