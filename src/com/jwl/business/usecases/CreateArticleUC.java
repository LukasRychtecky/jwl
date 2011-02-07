package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ArticleExistsException;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.ICreateArticleUC;
import com.jwl.business.usecases.interfaces.ISaveTagsUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.dao.interfaces.ITagDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class CreateArticleUC extends AbstractUC implements ICreateArticleUC {

	private IArticleDAO dao;
	private ITagDAO tagDAO;

	public CreateArticleUC(IArticleDAO dao, ITagDAO tagDAO) {
		this.dao = dao;
		this.tagDAO = tagDAO;
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

			id = this.dao.create(article);

		} catch (DuplicateEntryException e) {
			throw new ArticleExistsException(e);
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		ISaveTagsUC uc = new SaveTagsUC(this.tagDAO);
		uc.save(tags, id);

		return id;
	}

}
