package com.jwl.integration.filesystem.history;

import java.util.Date;
import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.history.IHistoryDAO;

public class FSHistoryDAO implements IHistoryDAO {

	@Override
	public void create(HistoryTO history) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(HistoryId id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(ArticleId id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllYoungerThan(ArticleId id, Date date)
			throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<HistoryTO> findAll(ArticleId id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoryTO get(HistoryId id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(HistoryTO history) throws DAOException {
		// TODO Auto-generated method stub

	}

}
