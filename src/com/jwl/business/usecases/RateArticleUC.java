package com.jwl.business.usecases;

import java.util.Date;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.RatingTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.usecases.interfaces.IRateArticleUC;

import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.rating.IRatingDAO;

public class RateArticleUC extends AbstractUC implements IRateArticleUC {

	public RateArticleUC(IDAOFactory factory) {
		super(factory);
	}
	
	@Override		
	public void rateArticle(ArticleId articleId, float rating)throws ModelException {
		super.checkPermission(AccessPermissions.ARTICLE_RATE);
		String userName = Environment.getIdentity().getUserName();
		IKnowledgeManagementFacade kmf = Environment.getKnowledgeFacade();
		try {
			IRatingDAO rdao= this.factory.getRatingDAO();
			RatingTO rat = rdao.find(articleId, userName);
			if(rat==null){
				rat =new RatingTO();
				rat.setAuthor(userName);
				rat.setModified(new Date());
				rat.setRating(rating);
				rdao.create(rat, articleId);
			}else{
				rat.setModified(new Date());
				kmf.revertArticleRatingLivability(articleId, rat.getRating());
				rat.setRating(rating);				
				rdao.update(rat, articleId);
			}
			kmf.handleArticleRatingLivability(articleId, rating);
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
