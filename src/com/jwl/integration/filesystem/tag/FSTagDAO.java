package com.jwl.integration.filesystem.tag;

import java.util.Set;

import com.jwl.business.article.ArticleId;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.tag.ITagDAO;
import java.util.List;

public class FSTagDAO implements ITagDAO {

	@Override
	public void addExistingToArticle(Set<String> tags, ArticleId id)
			throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void create(String tag, ArticleId id) throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void create(Set<String> tags, ArticleId id) throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<String> getAll() throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Set<String> getAllWhere(Set<String> tags) throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Set<String> getAllWhere(ArticleId id) throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeFromArticle(Set<String> tags, ArticleId id)
			throws DAOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
