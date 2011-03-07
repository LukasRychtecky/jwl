/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jwl.integration.filesystem;

import com.jwl.integration.IDAOFactory;
import com.jwl.integration.article.IArticleDAO;
import com.jwl.integration.history.IHistoryDAO;
import com.jwl.integration.role.IRoleDAO;
import com.jwl.integration.tag.ITagDAO;

/**
 *
 * @author ostatnickyjiri
 */
public class FSDAOFactory implements IDAOFactory {

    @Override
    public IArticleDAO getArticleDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ITagDAO getTagDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IHistoryDAO getHistoryDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IRoleDAO getRoleDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
