package com.jwl.business.article;

import java.util.Date;

import com.jwl.integration.entity.Article;

public interface IKeyWord {

	public String getWord();

	public void setWord(String word);

	public Double getWeight();

	public void setWeight(Double weight);

	public Date getCreated();

	public void setCreated(Date created);

	public Article getArticle();

	public void setArticle(Article article);

}