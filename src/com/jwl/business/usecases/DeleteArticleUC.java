package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.ObjectNotFoundException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IDeleteArticleUC;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.dao.interfaces.IHistoryDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.EntityNotFoundException;

/**
 *
 * @author Lukas Rychtecky
 */
public class DeleteArticleUC extends AbstractUC implements IDeleteArticleUC {

	private IArticleDAO dao;
	private IHistoryDAO historyDAO;

	public DeleteArticleUC(IArticleDAO dao, IHistoryDAO historyDAO) {
		this.dao = dao;
		this.historyDAO = historyDAO;
	}

	@Override
	public void delete(ArticleId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_DELETE);
		try {
			ArticleTO article = this.dao.get(id);

			if (article == null) {
				throw new ObjectNotFoundException("Article not found, id: " + id);
			}

			if (article.isLocked()) {
				throw new BreakBusinessRuleException("Can't delete locked article.");
			}

			this.dao.delete(id);
			this.historyDAO.deleteAll(id);
		} catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException("Article not found, id: " + id, e);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
