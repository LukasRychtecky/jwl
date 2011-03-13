package com.jwl.integration.convertor;

import com.jwl.business.article.ArticleId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jwl.business.article.ArticleTO;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.Tag;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ArticleConvertor {

	public static List<ArticleTO> convertList(List<Article> list) {
		List<ArticleTO> result = new ArrayList<ArticleTO>();
		Iterator<Article> iterator = list.iterator();

		while (iterator.hasNext()) {
			Article article = iterator.next();
			ArticleTO articleTO = ArticleConvertor.convertFromEntity(article);

			result.add(articleTO);
		}
		return result;
	}

	public static ArticleTO convertFromEntity(Article entity) {
		ArticleTO article = new ArticleTO(new ArticleId(entity.getId()),
				entity.getModified());
		article.setChangeNote(entity.getChangeNote());
		article.setCreated(entity.getCreated());
		article.setEditCount(entity.getEditCount());
		article.setEditor(entity.getEditor());
		article.setLocked(entity.isLocked());
		article.setText(entity.getText());
		article.setTitle(entity.getTitle());

		for (Tag tag : entity.getTags()) {
			article.addTag(tag.getName());
		}

		article.setRatings(RatingConvertor.convertFromEntities(entity
				.getRatings()));
		
		article.setKeyWords(KeyWordConvertor.fromEntities(entity.getKeyWords()));
		return article;
	}

	public static Article convertToEntity(ArticleTO article) {
		Article entity = new Article();
		entity.setTitle(article.getTitle());
		entity.setText(article.getText());
		entity.setCreated(new Date());
		entity.setLocked(article.isLocked());
		entity.setEditor(article.getEditor());
		entity.setModified(new Date());
		entity.setEditCount(article.getEditCount());
		entity.setChangeNote(article.getChangeNote());
		if (article.getId() != null) {
			entity.setId(article.getId().getId());
		}

		Set<Tag> tags = new HashSet<Tag>();

		for (String tag : article.getTags()) {
			tags.add(new Tag(tag));
		}

		entity.setTags(tags);

		return entity;
	}

}
