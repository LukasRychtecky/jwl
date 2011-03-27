package com.jwl.business.article;

import java.util.Date;

public class KeyWordTO {
	private int id;
	private String word;
	private Double weight;
	private Date created;
	private ArticleTO article;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public ArticleTO getArticle() {
		return article;
	}

	public void setArticle(ArticleTO article) {
		this.article = article;
	}

}
