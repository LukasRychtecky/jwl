package com.jwl.business.usecases;

import java.util.Date;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.RatingTO;
import com.jwl.business.exceptions.ModelException;
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
		try {
			IRatingDAO rdao= this.factory.getRAtingDAO();
			RatingTO rat = rdao.find(articleId, "Petr");
			if(rat==null){
				rat =new RatingTO();
				rat.setAuthor("Petr");
				rat.setModified(new Date());
				rat.setRating(rating);
				rdao.create(rat, articleId);
			}else{
				rat.setModified(new Date());
				rat.setRating(rating);
				rdao.update(rat, articleId);
			}
		} catch (DAOException e) {
			throw new ModelException(e);
		}
	}

}
