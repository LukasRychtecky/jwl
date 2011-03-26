package com.jwl.integration.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Petr
 */
@Entity
@Table(name = "topic", catalog = "wiki", schema = "")
@NamedQueries({
		@NamedQuery(name = "Topic.findAll", query = "SELECT t FROM Topic t"),
		@NamedQuery(name = "Topic.findById",
				query = "SELECT t FROM Topic t WHERE t.id = :id"),
		@NamedQuery(name = "Topic.findByTitle",
				query = "SELECT t FROM Topic t WHERE t.title = :title"),
		@NamedQuery(name = "Topic.findByClosed",
				query = "SELECT t FROM Topic t WHERE t.closed = :closed") })
public class Topic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "closed")
	private Boolean closed;
	
	@JoinColumn(name = "article_id", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Article article;
	
	@OneToMany(cascade = { CascadeType.REMOVE}, mappedBy = "topic")
	private List<Post> posts;
	

	public Topic() {
	}

	public Topic(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> postList) {
		this.posts = postList;
	}

}
