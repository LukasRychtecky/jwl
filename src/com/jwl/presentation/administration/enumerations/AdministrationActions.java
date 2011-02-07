package com.jwl.presentation.administration.enumerations;

/**
 *
 * @author lukas
 */
public enum AdministrationActions {

	DELETE ("delete"),
	UNKNOWN ("unknow"),
	LOCK ("lock"),
	UNLOCK ("unlock"),
	EDIT("edit"),
	VIEW("view"),
	HISTORY_VIEW ("history_view"),
	HISTORY_LIST ("history_list"),
	RESTORE ("restore");

	public String action;

	private AdministrationActions(String action) {
		this.action = action;
	}

}
