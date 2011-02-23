package com.jwl.integration.history;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.integration.exceptions.DAOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IHistoryDAO {

	public void create(HistoryTO history) throws DAOException;

	public HistoryTO get(HistoryId id) throws DAOException;

	public void update(HistoryTO history) throws DAOException;

	public void delete(HistoryId id) throws DAOException;

	public void deleteAll(ArticleId id) throws DAOException;

	public void deleteAllYoungerThan(ArticleId id, Date date) throws DAOException;

	public List<HistoryTO> findAll(ArticleId id) throws DAOException;

}
