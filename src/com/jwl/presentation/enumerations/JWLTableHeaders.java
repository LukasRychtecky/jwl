package com.jwl.presentation.enumerations;

public enum JWLTableHeaders {
	
	BLANK(""),
	TITLE("Title"), 
	TAGS("Tags"), 
	EDITOR("Editor"), 
	EDITING_COUNT("Editing count"), 
	CREATED("Created"), 
	RATING("Rating"), 
	ACTIONS("Actions"),
	TOPIC("Topic"),
	REPLIES("Replies");
	
	public final String value;
	
	private JWLTableHeaders(String value) {
		this.value = value;
	}
	
	protected final String[] headers = new String[] { "", };
	
}
