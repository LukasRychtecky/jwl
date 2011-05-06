package com.jwl.integration.article;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.integration.exceptions.DAOException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IArticleDAO {


	public ArticleId create(ArticleTO article) throws DAOException;

	public ArticleTO get(ArticleId id) throws DAOException;

	public void update(ArticleTO article) throws DAOException;

	public void delete(ArticleId id) throws DAOException;

	public List<ArticleTO> findAll() throws DAOException;
	
	public List<ArticleTO> findEverywhere(String needle) throws DAOException;

	public ArticleTO getByTitle(String title) throws DAOException;
	
	public List<ArticleTO> findAll(int from, int maxCount) throws DAOException;
	
	public int getCount()throws DAOException;
	
	public List<ArticleTO> findArticleWithKeyWord(Set<String> keyWords)throws DAOException;
	
	public List<ArticleTO> findDead() throws DAOException;
	
	public List<ArticleTO> fullScanSearch(Set<String> searchWords) throws DAOException;
}
