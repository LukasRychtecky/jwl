package com.jwl.business.article;

import java.util.Date;

/**
 *
 * @author Lukas Rychtecky
 */
public class HistoryTO {

	private HistoryId id;
	private String text;
	private String title;
	private String editor;
	private Date modified;
	private String changeNote;

	public HistoryTO() {
	}

	public HistoryTO(HistoryId id, Date modified) {
		this.id = id;
		this.modified = modified;
	}

	public HistoryTO(HistoryId id, String text, String title, String editor, Date modified, String changeNote) {
		this.id = id;
		this.text = text;
		this.title = title;
		this.editor = editor;
		this.modified = modified;
		this.changeNote = changeNote;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String changeNote) {
		this.changeNote = changeNote;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public HistoryId getId() {
		return id;
	}

	public void setId(HistoryId id) {
		this.id = id;
	}

	public Date getModified() {
		return modified;
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

}
