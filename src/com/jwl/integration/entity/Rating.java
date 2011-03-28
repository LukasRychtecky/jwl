package com.jwl.integration.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "rating", catalog = "wiki", schema = "")
public class Rating {
	
	@EmbeddedId
    protected RatingPK pk;
    
	@Basic(optional = false)
    @Column(name = "rating", nullable = false)
    private float rating;
   
    @Basic(optional = false)
    @Column(name = "modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Article article;

	public Rating() {

	}
	
	public Rating(int articleId, String author) {
		this.pk = new RatingPK(articleId, author);
	}

	public Rating(int articleId, String author, Article article, float rating,
			Date modified) {
		this.pk = new RatingPK(articleId, author);
		this.article = article;
		this.rating = rating;
		this.modified = modified;
	}	

	public int getArticleId() {
		return pk.getArticleId();
	}

	public String getAuthor() {
		return pk.getAuthor();
	}

	public Article getArticle() {
		return article;
	} 

	public void setArticle(Article article) {
		this.article = article;
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

}
