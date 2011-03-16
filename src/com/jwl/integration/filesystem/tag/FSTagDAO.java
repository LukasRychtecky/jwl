package com.jwl.integration.filesystem.tag;

import java.util.Set;

import com.jwl.business.article.ArticleId;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.tag.ITagDAO;

public class FSTagDAO implements ITagDAO {

	@Override
	public void addExistingToArticle(Set<String> tags, ArticleId id)
			throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(String tag, ArticleId id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(Set<String> tags, ArticleId id) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getAllWhere(Set<String> tags) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getAllWhere(ArticleId id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeFromArticle(Set<String> tags, ArticleId id)
			throws DAOException {
		// TODO Auto-generated method stub

	}

}
