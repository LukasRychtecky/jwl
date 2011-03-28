package com.jwl.integration.entity;

// Generated 22.10.2010 10:15:44 by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.jwl.integration.BaseEntity;
import com.jwl.integration.role.RoleEntity;

/**
 * Article generated by hbm2java
 */
@NamedQueries({
		@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
		@NamedQuery(name = "Article.findByTitle",
				query = "SELECT a FROM Article a WHERE a.title = ?0"),
		@NamedQuery(
				name = "Article.findEverywhere",
				query = "SELECT a FROM Article a WHERE UPPER(a.title) LIKE ?0 OR UPPER(a.text) LIKE ?0 OR UPPER(a.editor) LIKE ?0"),
		// @NamedQuery(name = "Article.findEverywhere", query =
		// "SELECT a FROM Article a, Tag t WHERE a.id=t.id AND UPPER(a.title) LIKE ?0 OR UPPER(a.text) LIKE ?0 OR UPPER(a.editor) LIKE ?0")
		// @NamedQuery(name = "Article.findEverywhere", query =
		// "SELECT a FROM Article a JOIN a.tags tagref WHERE UPPER(a.title) LIKE ?0 OR UPPER(a.text) LIKE ?0 OR UPPER(a.editor) LIKE ?0")
		@NamedQuery(name = "Article.allOrderedByTitleASC",
				query = "SELECT a FROM Article a ORDER BY a.title ASC"),
		@NamedQuery(name = "Article.allOrderedByTitleDESC",
				query = "SELECT a FROM Article a ORDER BY a.title DESC"),
		@NamedQuery(name = "Article.allOrderedByCreatedASC",
				query = "SELECT a FROM Article a ORDER BY a.created ASC"),
		@NamedQuery(name = "Article.allOrderedByCreatedDESC",
				query = "SELECT a FROM Article a ORDER BY a.created DESC"),
		@NamedQuery(name = "Article.allOrderedByEdiotrASC",
				query = "SELECT a FROM Article a ORDER BY a.editor ASC"),
		@NamedQuery(name = "Article.allOrderedByEditorDESC",
				query = "SELECT a FROM Article a ORDER BY a.editor DESC"),
		@NamedQuery(name = "Article.count",
				query = "SELECT count(a) FROM Article a"),
		@NamedQuery(name = "Article.findDead",
				query = "SELECT a FROM Article a where a.livability <= 0") })
@Entity
@Table(name = "article", catalog = "wiki", schema = "",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Article extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8292980447188134233L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "text", length = 16777215)
	private String text;

	@Column(name = "title", nullable = false, length = 255)
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	private Date created;

	@Column(name = "locked", nullable = false)
	private boolean locked;

	@Column(name = "editor", nullable = false, length = 100)
	private String editor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified", nullable = false, length = 19)
	private Date modified;

	@Column(name = "editCount", nullable = false)
	private int editCount;

	@Column(name = "changeNote", length = 255)
	private String changeNote;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "article_exclude_role", joinColumns = { @JoinColumn(
			name = "article_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "role_id",
					nullable = false, updatable = false) })
	private Set<RoleEntity> roles = new HashSet<RoleEntity>(0);

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "article",
			fetch = FetchType.LAZY)
	private Collection<History> histories;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "article_has_tag", joinColumns = { @JoinColumn(
			name = "article_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "tag_id",
					nullable = false, updatable = false) })
	@OrderBy(value = "name")
	private Set<Tag> tags = new HashSet<Tag>(0);

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "article_has_attachment", joinColumns = { @JoinColumn(
			name = "article_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "attachment_id",
					nullable = false, updatable = false) })
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	@OneToMany(mappedBy = "article", cascade = { CascadeType.REMOVE })
	private List<Rating> ratings;

	@OneToMany(mappedBy = "article", cascade = { CascadeType.REMOVE })
	private List<KeyWord> keyWords;

	@Column(name = "livability", nullable = false)
	private double livability;
	
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "article")
    private List<Topic> topics;

	public Article() {
	}

	public Article(String title, Date created, boolean locked, String editor,
			Date modified, int editCount) {
		this.title = title;
		this.created = created;
		this.locked = locked;
		this.editor = editor;
		this.modified = modified;
		this.editCount = editCount;
	}

	public Article(String text, String title, Date created, boolean locked,
			String editor, Date modified, int editCount, String changeNote,
			Set<RoleEntity> roles, Set<History> histories, Set<Tag> tags,
			Set<Attachment> attachments) {
		this.text = text;
		this.title = title;
		this.created = created;
		this.locked = locked;
		this.editor = editor;
		this.modified = modified;
		this.editCount = editCount;
		this.changeNote = changeNote;
		this.roles = roles;
		this.histories = histories;
		this.tags = tags;
		this.attachments = attachments;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public int getEditCount() {
		return this.editCount;
	}

	public void setEditCount(int editCount) {
		this.editCount = editCount;
	}

	public String getChangeNote() {
		return this.changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public Set<RoleEntity> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public Collection<History> getHistoryCollection() {
		return histories;
	}

	public void setHistoryCollection(Collection<History> historyCollection) {
		this.histories = historyCollection;
	}

	public Set<Tag> getTags() {
		return this.tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<KeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<KeyWord> keyWords) {
		this.keyWords = keyWords;
	}

	public double getLivability() {
		return livability;
	}

	public void setLivability(double livability) {
		this.livability = livability;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	
}
