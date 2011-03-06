package com.jwl.business.article;

import java.util.Date;

public class RatingTO {

	private String author;
	private float rating;
	private Date modified;
	private ArticleId articleId;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;

	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;

	}

	public ArticleId getArticleId() {
		return articleId;
	}

	public void setArticleId(ArticleId articleId) {
		this.articleId = articleId;
	}
	
}
