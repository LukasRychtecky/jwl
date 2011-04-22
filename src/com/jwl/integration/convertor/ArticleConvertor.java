package com.jwl.integration.convertor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.AttachmentTO;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.Attachment;
import com.jwl.integration.entity.Tag;

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
		ArticleTO article = new ArticleTO(new ArticleId(entity.getId()), entity
				.getModified());
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

		article.setAttachments(AttachmentConvertor.convertFromEntity(entity.getAttachments()));

		article.setRatings(RatingConvertor.convertFromEntities(entity.getRatings()));

		article.setKeyWords(KeyWordConvertor.fromEntities(entity.getKeyWords()));

		// for (RoleEntity roleEntity : entity.getRoles()) {
		// article.addRole(RoleConvertor.toObject(roleEntity));
		// }

		article.setLivability(entity.getLivability());

		article.setTopics(TopicConverter
				.convertFromEntities(entity.getTopics()));

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

		Set<Attachment> atts = new HashSet<Attachment>();
		for (AttachmentTO attTO : article.getAttachments()) {
			String title = attTO.getTitle();
			String origName = attTO.getOriginalName();
			String uniqeName = attTO.getUniqueName();
			String desc = attTO.getDescription();
			Attachment att = new Attachment(title, origName, uniqeName, desc);
			att.setId(attTO.getId());
			atts.add(att);
			//atts.add(new Attachment(att.getTitle(), att.getOriginalName(), att.getUniqueName(), att.getDescription()));
		}
		entity.setAttachments(atts);

		entity.setLivability(article.getLivability());
		return entity;
	}

}
