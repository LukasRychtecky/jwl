package com.jwl.integration.convertor;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.History;
import com.jwl.integration.entity.HistoryPK;

/**
 *
 * @author Lukas Rychtecky
 */
public class HistoryConvertor {

	public static HistoryTO convertFromEntity(History entity) {
		HistoryTO history = new HistoryTO(new HistoryId(entity.getHistoryPK().getId(), new ArticleId(entity.getArticle().getId())), entity.getModified());
		history.setChangeNote(entity.getChangeNote());
		history.setEditor(entity.getEditor());
		history.setText(entity.getText());
		history.setTitle(entity.getTitle());

		return history;
	}

	public static History convertToEntity(HistoryTO history) {
		History entity = new History();
		entity.setChangeNote(history.getChangeNote());
		entity.setEditor(history.getEditor());
		entity.setModified(history.getModified());
		entity.setText(history.getText());
		entity.setTitle(history.getTitle());

		Article article = new Article();
		article.setId(history.getId().getArticleId().getId());
		entity.setArticle(article);
		if (history.getId() != null) {
			entity.setHistoryPK(new HistoryPK(history.getId().getId(), article.getId()));
		} else {
			entity.setHistoryPK(new HistoryPK(0, article.getId()));
		}

		return entity;
	}

}
