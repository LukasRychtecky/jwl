package com.jwl.presentation.component.renderer;

/**
 *
 * @author Lukas Rychtecky
 */
public enum FlashMessageType {
	
	INFO ("info"),
	WARNING ("warning"),
	ERROR ("error");

	private String type;

	private FlashMessageType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
