package com.jwl.business.article;

import com.jwl.business.AbstractId;

/**
 *
 * @author Lukas Rychtecky
 */
public class HistoryId extends AbstractId {

	private ArticleId articleId;

	public HistoryId(Integer id, ArticleId articleId) {
		super(id);
		if (articleId == null) {
			throw new IllegalArgumentException("Article id can't be null.");
		}
		this.articleId = articleId;
	}

	public ArticleId getArticleId() {
		return this.articleId;
	}

}
