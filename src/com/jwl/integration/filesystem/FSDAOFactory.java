package com.jwl.integration.filesystem;

import com.jwl.integration.IDAOFactory;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.filesystem.article.FSArticleDAO;
import com.jwl.integration.filesystem.history.FSHistoryDAO;
import com.jwl.integration.filesystem.tag.FSTagDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.keyword.IKeyWordDAO;
import com.jwl.integration.post.IPostDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;
import com.jwl.integration.topic.ITopicDAO;

/**
 *
 * @author ostatnickyjiri
 */
public class FSDAOFactory implements IDAOFactory {

    @Override
    public IArticleDAO getArticleDAO() {
        return new FSArticleDAO();
    }

    @Override
    public ITagDAO getTagDAO() {
        return new FSTagDAO();
    }

    @Override
    public IHistoryDAO getHistoryDAO() {
        return new FSHistoryDAO();
    }

    @Override
    public IRoleDAO getRoleDAO() {
    	throw new UnsupportedOperationException("Not supported yet.");
    }

	@Override
	public IRatingDAO getRatingDAO() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public IKeyWordDAO getKeyWordDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITopicDAO getTopicDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPostDAO getPostDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
