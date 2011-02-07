package com.jwl.business;

/**
 *
 * @author lukas
 */
public enum RoleTypes {
	ADMINISTRATOR ("administrator"),
	EDITOR ("editor"),
	VISITOR ("visitor");

	private String role;

	private RoleTypes(String role) {
		this.role = role;
	}

}
