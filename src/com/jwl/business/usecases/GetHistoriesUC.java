package com.jwl.business.usecases;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.usecases.interfaces.IGetHistoriesUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetHistoriesUC extends AbstractUC implements IGetHistoriesUC {

	public GetHistoriesUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<HistoryTO> get(ArticleId id) throws ModelException {
		List<HistoryTO> list = new ArrayList<HistoryTO>();

		try {
			list.addAll(super.factory.getHistoryDAO().findAll(id));
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		return list;
	}

}
