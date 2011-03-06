package com.jwl.integration.convertor;

import java.util.ArrayList;
import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.RatingTO;
import com.jwl.integration.entity.Rating;

public class RatingConvertor {

	public static RatingTO convertFromEntity(Rating entity){
		RatingTO rating = new RatingTO();
		rating.setAuthor(entity.getAuthor());
		rating.setModified(entity.getModified());
		rating.setRating(entity.getRating());
		return rating;
	}
	
	public static Rating convertToEntity(RatingTO rating, ArticleId articleId){
		Rating entity = new Rating(articleId.getId(), rating.getAuthor());
		entity.setModified(rating.getModified());
		entity.setRating(rating.getRating());
		return entity;
	}
	
	public static List<RatingTO> convertFromEntities(List<Rating> entityList){
		List<RatingTO> result = new ArrayList<RatingTO>();
		for(Rating entity: entityList){
			RatingTO r = convertFromEntity(entity);
			result.add(r);
		}
		return result;
	}
}
