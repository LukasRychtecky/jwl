package com.jwl.integration.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Lukas Rychtecky
 */
@Embeddable
public class HistoryPK implements Serializable {
	private static final long serialVersionUID = 8292980447188134233L;
	@Basic(optional = false)
    @Column(name = "id", nullable = false)
	private int id;
	@Basic(optional = false)
    @Column(name = "article_id", nullable = false)
	private int articleId;

	public HistoryPK() {
	}

	public HistoryPK(int id, int articleId) {
		this.id = id;
		this.articleId = articleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += id;
		hash += articleId;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof HistoryPK)) {
			return false;
		}
		HistoryPK other = (HistoryPK) object;
		if (this.id != other.id) {
			return false;
		}
		if (this.articleId != other.articleId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.jwl.integration.dao.HistoryPK[id=" + id + ", articleId=" + articleId + "]";
	}

}
