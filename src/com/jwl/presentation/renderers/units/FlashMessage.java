package com.jwl.presentation.renderers.units;


/**
 *
 * @author Lukas Rychtecky
 */
public class FlashMessage {

	private Boolean hide;
	private String message;
	private FlashMessageType type;

	public FlashMessage(String message) {
		this(message, FlashMessageType.INFO);
	}

	public FlashMessage(String message, FlashMessageType type) {
		this(message, type, Boolean.TRUE);
	}

	public FlashMessage(String message, FlashMessageType type, Boolean hide) {
		this.message = message;
		this.type = type;
		this.hide = hide;
	}

	public Boolean isHide() {
		return hide;
	}

	public String getMessage() {
		return message;
	}

	public FlashMessageType getType() {
		return type;
	}
	
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

	
}
