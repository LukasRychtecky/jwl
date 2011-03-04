package com.jwl.business.article;

import java.util.Date;

import com.jwl.integration.entity.Article;

public interface IRating {

	public String getAuthor();

	public void setAuthor(String author);

	public Article getArticle();

	public void setArticle(Article article);

	public float getRating();

	public void setRating(float rating);

	public Date getModified();

	public void setModified(Date modified);

}