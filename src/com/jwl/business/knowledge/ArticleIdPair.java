package com.jwl.business.knowledge;

import java.io.Serializable;

import com.jwl.business.article.ArticleId;

public class ArticleIdPair implements Serializable {

	private static final long serialVersionUID = -5109069986952544134L;
	private Integer id1;
	private Integer id2;

	public ArticleIdPair(ArticleId id1, ArticleId id2) {
		if (id1.getId() < id2.getId()) {
			this.id1 = id1.getId();
			this.id2 = id2.getId();
		} else {
			this.id1 = id2.getId();
			this.id2 = id1.getId();
		}
	}

	public ArticleId getId1() {
		return new ArticleId(id1);
	}

	public ArticleId getId2() {
		return new ArticleId(id2);
	}
}
