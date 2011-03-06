package com.jwl.integration.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RatingPK implements Serializable {

	private static final long serialVersionUID = -4719384797956118519L;
	
	@Basic(optional = false)
    @Column(name = "article_id", nullable = false)
	private int articleId;

	@Basic(optional = false)
    @Column(name = "author", nullable = false, length = 100)
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
