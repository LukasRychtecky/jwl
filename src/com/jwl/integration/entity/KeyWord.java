package com.jwl.integration.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jwl.business.article.IKeyWord;

@Entity
@Table(name = "key_word", catalog = "wiki", schema = "")
public class KeyWord implements IKeyWord {
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

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#getWord()
	 */
	@Override
	public String getWord() {
		return word;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#setWord(java.lang.String)
	 */
	@Override
	public void setWord(String word) {
		this.word = word;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#getWeight()
	 */
	@Override
	public Double getWeight() {
		return weight;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#setWeight(java.lang.Double)
	 */
	@Override
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#getCreated()
	 */
	@Override
	public Date getCreated() {
		return created;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#setCreated(java.util.Date)
	 */
	@Override
	public void setCreated(Date created) {
		this.created = created;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#getArticle()
	 */
	@Override
	public Article getArticle() {
		return article;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IKeyWord#setArticle(com.jwl.integration.entity.Article)
	 */
	@Override
	public void setArticle(Article article) {
		this.article = article;
	}	

}
