package com.jwl.business.knowledge.exceptions;

public class KnowledgeManagementSettingsException extends Exception {

	private static final long serialVersionUID = 1L;

	public KnowledgeManagementSettingsException() {
	}

	public KnowledgeManagementSettingsException(String message) {
		super(message);
	}

	public KnowledgeManagementSettingsException(Throwable thr) {
		super(thr);
	}

	public KnowledgeManagementSettingsException(String message, Throwable thr) {
		super(message, thr);
	}

}
