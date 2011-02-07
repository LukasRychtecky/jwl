package com.jwl.business.usecases;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.usecases.interfaces.IGetHistoriesUC;
import com.jwl.integration.dao.interfaces.IHistoryDAO;
import com.jwl.integration.exceptions.DAOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetHistoriesUC extends AbstractUC implements IGetHistoriesUC {

	private IHistoryDAO dao;

	public GetHistoriesUC(IHistoryDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<HistoryTO> get(ArticleId id) throws ModelException {
		List<HistoryTO> list = new ArrayList<HistoryTO>();

		try {
			list.addAll(this.dao.findAll(id));
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		return list;
	}

}
