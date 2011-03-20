package com.jwl.integration.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@NamedQueries({
	@NamedQuery(name ="KeyWord.findAll",query="SELECT kw FROM KeyWord kw"),
	@NamedQuery(name = "KeyWord.best",
			query = "SELECT  max(kw.weight) FROM KeyWord kw JOIN kw.article GROUP BY kw.article  ")
})
@Entity
@Table(name = "key_word", catalog = "wiki", schema = "")
public class KeyWord {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
		
	
	@Column(name = "word", nullable = false)
	private String word;
	
	@Column(name = "weight", nullable = false)
	private Double weight;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false)
	private Date created;

	@ManyToOne(optional = false)
	@JoinColumn(name = "article_id", referencedColumnName = "id")
	private Article article;
	
	public KeyWord(){
		
	}

	public KeyWord(String word, Double weight, Date created, Article article) {
		super();
		this.word = word;
		this.weight = weight;
		this.created = created;
		this.article = article;
	}

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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}	

}
