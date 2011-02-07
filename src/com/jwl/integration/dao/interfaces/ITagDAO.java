package com.jwl.integration.dao.interfaces;

import com.jwl.business.article.ArticleId;
import com.jwl.integration.exceptions.DAOException;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface ITagDAO {

	public void create(String tag, ArticleId id) throws DAOException;

	public void create(Set<String> tags, ArticleId id) throws DAOException;

	public Set<String> getAll() throws DAOException;

	public Set<String> getAllWhere(Set<String> tags) throws DAOException;

	public Set<String> getAllWhere(ArticleId id) throws DAOException;

	public void addExistingToArticle(Set<String> tags, ArticleId id) throws DAOException;

	public void removeFromArticle(Set<String> tags, ArticleId id) throws DAOException;

}
