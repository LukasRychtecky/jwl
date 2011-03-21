package com.jwl.business.usecases;

import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetHistoryUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetHistoryUC extends AbstractUC implements IGetHistoryUC {

	public GetHistoryUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public HistoryTO get(HistoryId id) throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_RESTORE);
		HistoryTO history = null;

		try {
			history = super.factory.getHistoryDAO().get(id);
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		return history;
	}

}
