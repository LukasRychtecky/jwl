package com.jwl.business.article;

import com.jwl.business.security.Role;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ArticleTO {

	public static final int TITLE_MAX_SIZE = 255;
	public static final int EDITOR_MAX_SIZE = 100;
	public static final int CHANGE_NOTE_MAX_SIZE = 255;

	private ArticleId id;
	private String title = "";
	private String text = "";
	private Date created = new Date();
	private String editor = "";
	private Integer editCount = 0;
	private Set<String> tags = new HashSet<String>();
	private Boolean locked = Boolean.FALSE;
	private Date modified;
	private String changeNote = "";
	private List<RatingTO> ratings;
	private Set<Role> excludedRoles;
	private List<KeyWordTO> keyWords;
	private Set<AttachmentTO> attachments;
	

	public ArticleTO() {
	}

	public ArticleTO(ArticleId id, Date modified) {
		this.id = id;
		this.modified = modified;
		this.excludedRoles = new HashSet<Role>();
	}

	/**
	 * Returns id or null
	 *
	 * @return ArticleId|null
	 */
	public ArticleId getId() {
		return id;
	}

	public void setId(ArticleId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws IllegalArgumentException {
		if (title != null) {
			if (title.length() > ArticleTO.TITLE_MAX_SIZE) {
				throw new IllegalArgumentException("Title is too long, max size: " + ArticleTO.TITLE_MAX_SIZE);
			}
			this.title = title;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text != null) {
			this.text = text;
		}
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		if (created != null) {
			this.created = created;
		}
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) throws IllegalArgumentException {
		if (editor != null) {
			if (editor.length() > ArticleTO.EDITOR_MAX_SIZE) {
				throw new IllegalArgumentException("Editor is too long, max size: " + ArticleTO.EDITOR_MAX_SIZE);
			}
			this.editor = editor;
		}
	}

	public Integer getEditCount() {
		return editCount;
	}

	public void setEditCount(Integer editCount) throws IllegalArgumentException {
		if (editCount == null) {
			return;
		}
		if(editCount < 0) {
			throw new IllegalArgumentException("Count editions can't be negative value.");
		} else {
			this.editCount = editCount;
		}
	}

	public Boolean isLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		if (locked != null) {
			this.locked = locked;
		}
	}

	/**
	 * Returns modified date or null
	 *
	 * @return Date|null
	 */
	public Date getModified() {
		return modified;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) throws IllegalArgumentException {
		if (changeNote != null) {
			if (changeNote.length() > ArticleTO.CHANGE_NOTE_MAX_SIZE) {
				throw new IllegalArgumentException("Change note is too long, max size: " + ArticleTO.CHANGE_NOTE_MAX_SIZE);
			}
			this.changeNote = changeNote;
		}
	}

	public HistoryTO createHistory() {
		HistoryTO history = new HistoryTO();
		history.setChangeNote(this.changeNote);
		history.setEditor(this.editor);
		history.setText(this.text);
		history.setTitle(this.title);
		return history;
	}

	public Boolean addTag(String tag) {
		if (tag == null) {
			return false;
		}
		String trimmed = tag.trim();
		if (trimmed.length() > 0) {
			return this.tags.add(trimmed.toLowerCase());
		}
		return Boolean.FALSE;
	}

	public Boolean removeTag(String tag) {
		if (tag == null) {
			return false;
		}
		return this.tags.remove(tag);
	}

	public Set<String> getTags() {
		return new HashSet<String>(this.tags);
	}

	public void removeAllTags() {
		this.tags.clear();
	}

	public List<RatingTO> getRatings() {
		return ratings;
	}
	
	public void addRole(Role role) {
		this.excludedRoles.add(role);
	}

	public void setRatings(List<RatingTO> ratings) {
		this.ratings = ratings;
	}
	
	public Set<Role> getExcludedRoles() {
		return new HashSet<Role>(this.excludedRoles);
	}

	public List<KeyWordTO> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<KeyWordTO> keyWords) {
		this.keyWords = keyWords;
	}
	
	public float getRatingAverage(){
		float total =0;
		for(RatingTO r:ratings){
			total += r.getRating();
		}
		total/=ratings.size();
		return total;
	}

	public Set<AttachmentTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<AttachmentTO> attachments) {
		this.attachments = attachments;
	}
		
}
