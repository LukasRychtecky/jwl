package com.jwl.business.usecases;

import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetHistoryUC;
import com.jwl.integration.dao.interfaces.IHistoryDAO;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetHistoryUC extends AbstractUC implements IGetHistoryUC {
	
	private IHistoryDAO dao;

	public GetHistoryUC(IHistoryDAO dao) {
		this.dao = dao;
	}

	@Override
	public HistoryTO get(HistoryId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_RESTORE);
		HistoryTO history = null;

		try {
			history = this.dao.get(id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		return history;
	}

}
