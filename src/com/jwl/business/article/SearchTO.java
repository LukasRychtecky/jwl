package com.jwl.business.article;


public class SearchTO {
	
	private String searchText;
	private boolean everywhere;
	private boolean title;
	private boolean text;
	private boolean editors;
	private boolean tags;

	public SearchTO() {

	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isEverywhere() {
		return everywhere;
	}

	public void setEverywhere(boolean everywhere) {
		this.everywhere = everywhere;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public boolean isText() {
		return text;
	}

	public void setText(boolean text) {
		this.text = text;
	}

	public boolean isEditors() {
		return editors;
	}

	public void setEditors(boolean editors) {
		this.editors = editors;
	}

	public boolean isTags() {
		return tags;
	}

	public void setTags(boolean tags) {
		this.tags = tags;
	}

}
