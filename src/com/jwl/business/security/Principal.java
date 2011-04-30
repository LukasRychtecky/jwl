package com.jwl.business.security;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class Principal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private Set<Role> roles;

	public Principal(String username, Set<Role> roles) {
		this.username = username;
		this.roles = roles;
	}


	public Set<Role> getRoles() {
		return roles;
	}

	public String getUsername() {
		return username;
	}
	
	
	
}
