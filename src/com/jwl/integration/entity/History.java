package com.jwl.integration.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Lukas Rychtecky
 */
@Entity
@Table(name = "history", catalog = "wiki", schema = "")
@NamedQueries({
	@NamedQuery(name = "History.findAll", query = "SELECT h FROM History h"),
	@NamedQuery(name = "History.findById", query = "SELECT h FROM History h WHERE h.historyPK.id = :id"),
	@NamedQuery(name = "History.findByModified", query = "SELECT h FROM History h WHERE h.modified = :modified"),
	@NamedQuery(name = "History.findByArticleId", query = "SELECT h FROM History h WHERE h.historyPK.articleId = :articleId"),
	@NamedQuery(name = "History.deleteByArticleId", query = "DELETE FROM History h WHERE h.historyPK.articleId = :articleId"),
	@NamedQuery(name = "History.deleteByArticleIdAndYoungerThan", query = "DELETE FROM History h WHERE h.historyPK.articleId = :articleId AND h.modified > :currentDate")})
public class History implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected HistoryPK historyPK;
	@Lob
    @Column(name = "text", length = 16777215)
	private String text;
	@Basic(optional = false)
    @Column(name = "title", nullable = false, length = 255)
	private String title;
	@Basic(optional = false)
    @Column(name = "editor", nullable = false, length = 100)
	private String editor;
	@Basic(optional = false)
    @Column(name = "modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	@Column(name = "changeNote", length = 255)
	private String changeNote;
	@JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Article article;

	public History() {
	}

	public History(HistoryPK historyPK) {
		this.historyPK = historyPK;
	}

	public History(HistoryPK historyPK, String title, String editor, Date modified) {
		this.historyPK = historyPK;
		this.title = title;
		this.editor = editor;
		this.modified = modified;
	}

	public History(int id, int articleId) {
		this.historyPK = new HistoryPK(id, articleId);
	}

	public HistoryPK getHistoryPK() {
		return historyPK;
	}

	public void setHistoryPK(HistoryPK historyPK) {
		this.historyPK = historyPK;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (historyPK != null ? historyPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof History)) {
			return false;
		}
		History other = (History) object;
		if ((this.historyPK == null && other.historyPK != null) || (this.historyPK != null && !this.historyPK.equals(other.historyPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.jwl.integration.dao.History[historyPK=" + historyPK + "]";
	}

}
