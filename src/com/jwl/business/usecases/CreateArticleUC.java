package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ArticleExistsException;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.ICreateArticleUC;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class CreateArticleUC extends AbstractUC implements ICreateArticleUC {

	public CreateArticleUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public ArticleId create(ArticleTO article) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_EDIT);
		if (article.getTitle().isEmpty()) {
			throw new BreakBusinessRuleException("Article can't has empty title");
		}

		ArticleId id = null;
		Set<String> tags = article.getTags();
		
		try {
			article.removeAllTags();

			id = super.factory.getArticleDAO().create(article);

		} catch (DuplicateEntryException e) {
			throw new ArticleExistsException(e);
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		ISaveTagsUC uc = new SaveTagsUC(super.factory);
		uc.save(tags, id);

		return id;
	}

}
