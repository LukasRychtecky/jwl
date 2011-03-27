/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jwl.integration.filesystem.article;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;
import java.io.File;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ostatnickyjiri
 */
public class FSArticleDAO implements IArticleDAO {

    @Override
    public ArticleId create(ArticleTO article) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArticleTO get(ArticleId id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(ArticleTO article) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(ArticleId id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ArticleTO> findAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ArticleTO> findEverywhere(String needle) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArticleTO getByTitle(String title) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public List<ArticleTO> findAll(int from, int maxCount) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ArticleTO> findArticleWithKeyWord(Set<String> keyWords)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleTO> findDead() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
