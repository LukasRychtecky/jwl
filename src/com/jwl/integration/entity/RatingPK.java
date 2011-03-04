package com.jwl.integration.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class RatingPK implements Serializable {

	private static final long serialVersionUID = -4719384797956118519L;
	@Column(name = "article_id", unique = true, nullable = false,
			insertable = true)
	private int articleId;

	@Column(name = "author", unique = true, nullable = false, insertable = true)
	private String author;

	public RatingPK() {

	}

	public RatingPK(int articleId, String author) {
		this.articleId = articleId;
		this.author = author;
	}

	public int getArticleId() {
		return articleId;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RatingPK)) {
			return false;
		}
		RatingPK spk = (RatingPK) obj;
		return this.articleId == spk.getArticleId()
				&& this.author.equals(spk.getAuthor());
	}

	@Override
	public int hashCode() {
		return new Integer(articleId).hashCode() ^ this.author.hashCode();
	}
}
