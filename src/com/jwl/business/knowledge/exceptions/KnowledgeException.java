package com.jwl.business.knowledge.exceptions;

public class KnowledgeException extends Exception {
	private static final long serialVersionUID = 4955142418229692482L;

	public KnowledgeException() {
		super();
	}

	public KnowledgeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public KnowledgeException(String arg0) {
		super(arg0);
	}

	public KnowledgeException(Throwable arg0) {
		super(arg0);
	}
	
}
