package com.jwl.integration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jwl.business.article.IRating;

@Entity
@IdClass(RatingPK.class)
@Table(name = "rating", catalog = "wiki", schema = "")
public class Rating implements IRating {
	@Id
	@Column(name = "article_id", unique = true, nullable = false,
			insertable = true)
	private int articleId;
	

	@Id
	@Column(name = "author", unique = true, nullable = false,
			insertable = true)
	private String author;

	@ManyToOne(optional = false)
	@JoinColumn(name = "article_id", referencedColumnName = "id")
	private Article article;

	@Column(name = "rating", nullable = false)
	private float rating;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", nullable = false, length = 20)
	private Date modified;

	public Rating() {

	}

	public Rating(int articleId, String author, Article article, float rating,
			Date modified) {
		super();
		this.articleId = articleId;
		this.author = author;
		this.article = article;
		this.rating = rating;
		this.modified = modified;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#getAuthor()
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#setAuthor(java.lang.String)
	 */
	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#getArticle()
	 */
	@Override
	public Article getArticle() {
		return article;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#setArticle(com.jwl.integration.entity.Article)
	 */
	@Override
	public void setArticle(Article article) {
		this.article = article;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#getRating()
	 */
	@Override
	public float getRating() {
		return rating;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#setRating(float)
	 */
	@Override
	public void setRating(float rating) {
		this.rating = rating;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#getModified()
	 */
	@Override
	public Date getModified() {
		return modified;
	}

	/* (non-Javadoc)
	 * @see com.jwl.integration.entity.IRating#setModified(java.util.Date)
	 */
	@Override
	public void setModified(Date modified) {
		this.modified = modified;
	}

}
