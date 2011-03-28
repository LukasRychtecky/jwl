package com.jwl.integration.keyword;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.KeyWordTO;
import com.jwl.integration.exceptions.DAOException;

public interface IKeyWordDAO {
	public void create(KeyWordTO keyWord, ArticleId articleId) throws DAOException;
	public void delete(KeyWordTO keyWord) throws DAOException;
	public void deleteAll(ArticleId articleId) throws DAOException;
	public void create(List<KeyWordTO> keyWords, ArticleId articleId) throws DAOException;
	public List<KeyWordTO> getAll() throws DAOException;
}
