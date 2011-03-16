package com.jwl.integration.filesystem;

import com.jwl.integration.IDAOFactory;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.filesystem.article.FSArticleDAO;
import com.jwl.integration.filesystem.history.FSHistoryDAO;
import com.jwl.integration.filesystem.rating.FSRatingDAO;
import com.jwl.integration.filesystem.role.FSRoleDAO;
import com.jwl.integration.filesystem.tag.FSTagDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.rating.IRatingDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;

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
        return new FSRoleDAO();
    }

	@Override
	public IRatingDAO getRatingDAO() {
		return new FSRatingDAO();
	}

}
