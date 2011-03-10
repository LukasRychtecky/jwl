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

/**
 *
 * @author ostatnickyjiri
 */
public class FSArticleDAO implements IArticleDAO {

    @Override
    public ArticleId create(ArticleTO article) throws DAOException {
        File directory = new File("Enter any" +
                    "directory name or file name");
        boolean isDirectory = directory.isDirectory();
        if (isDirectory) {
          // It returns true if directory is a directory.
          System.out.println("the name you have entered" +
                 "is a directory  : "  +    directory);
          //It returns the absolutepath of a directory.
          System.out.println("the path is "  +
                      directory.getAbsolutePath());
        } else {
          // It returns false if directory is a file.
          System.out.println("the name you have" +
           "entered is a file  : " +   directory);
          //It returns the absolute path of a file.
          System.out.println("the path is "  +
                    directory.getAbsolutePath());
        }
        return null;
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

}
