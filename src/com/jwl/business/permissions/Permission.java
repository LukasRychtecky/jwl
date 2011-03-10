package com.jwl.business.permissions;

import com.jwl.business.article.ArticleId;

/**
 *
 * @author Lukas Rychtecky
 */
public class Permission {

	private AccessPermissions perm;

	public Permission(AccessPermissions perm) {
		this.perm = perm;
	}

	public AccessPermissions getPermission() {
		return this.perm;
	}

}
