package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
import com.jwl.integration.dao.interfaces.ITagDAO;
import com.jwl.integration.exceptions.DAOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class SaveTagsUC extends AbstractUC implements ISaveTagsUC {

	private ITagDAO dao;

	public SaveTagsUC(ITagDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(Set<String> givenTags, ArticleId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_EDIT);
		try {
			Set<String> alreadyExistTags = this.dao.getAllWhere(givenTags);
			Set<String> tagsToCreate = new HashSet<String>();
			for (String tag : givenTags) {
				if (!alreadyExistTags.contains(tag)) {
					tagsToCreate.add(tag);
				}
			}

			this.dao.create(tagsToCreate, id);
			this.dao.addExistingToArticle(givenTags, id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
