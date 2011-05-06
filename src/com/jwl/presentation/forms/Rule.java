package com.jwl.presentation.forms;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.type.TrueFalseType;

/**
 *
 * @author Lukas Rychtecky
 */
public class Rule {
	
	private Validation type;
	private String message;
	private List<?> args;

	public Rule(Validation type, String message, List<?> args) {
		this.type = type;
		this.message = message;
		this.args = args;
	}

	public Rule(Validation type, String message) {
		this(type, message, new ArrayList());
	}

	public String getMessage() {
		return message;
	}
	
	public Boolean isValid() {
		return Boolean.TRUE;
	}

	public Validation getType() {
		return type;
	}

	public List<?> getArgs() {
		return args;
	}
	
}
