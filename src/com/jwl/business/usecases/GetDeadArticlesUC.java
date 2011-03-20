package com.jwl.business.usecases;

import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IGetDeadArticlesUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;

public class GetDeadArticlesUC extends AbstractUC implements IGetDeadArticlesUC {
	
	
	
	public GetDeadArticlesUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public List<ArticleTO> getDeadArticles() throws ModelException {
		checkPermission(AccessPermissions.KNOWLEDGE_ADMINISTER);
		List<ArticleTO> result = null;
		try{
			result = factory.getArticleDAO().findDead();
		}catch(DAOException e){
			throw new ModelException(e);
		}
		return result;
	}

}
