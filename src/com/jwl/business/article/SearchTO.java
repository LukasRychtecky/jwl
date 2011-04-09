package com.jwl.business.article;


public class SearchTO {
	
	private String searchPhrase;
	private boolean title;
	private boolean keyWords;
	private boolean editors;
	private boolean tags;

	public SearchTO() {

	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public boolean isKeyWords() {
		return keyWords;
	}

	public void setKeyWords(boolean text) {
		this.keyWords = text;
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

	@Override
	public String toString() {
		return String.format("SearchTO: [searchPhrase=%s] [title=%b] [keyWords=%b]" +
				" [editor=%b] [tags=%b]", searchPhrase, title, keyWords, editors,
				tags);
	}

}
