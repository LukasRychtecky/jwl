package com.jwl.presentation.global;

import java.util.UUID;

/**
 * This class generates UUID for elements in DOM.
 * 
 * @author Lukas Rychtecky
 * @review Petr janouch
 */
public class IdGenerator {

	/**
	 * Returns unique id
	 * 
	 * @return
	 */
	public static String generateId() {
		return "jwl-" + UUID.randomUUID().toString();
	}

}
