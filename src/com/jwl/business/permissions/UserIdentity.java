package com.jwl.business.permissions;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.NoAuthenticatedYetException;
import com.jwl.business.exceptions.NoRoleFoundException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.integration.role.IRoleDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lukas Rychtecky
 */
public class UserIdentity implements IIdentity {

	private Map<String, List<Permission>> permissions;
	private Boolean isAuthenticated = Boolean.FALSE;

	public UserIdentity() {
		this.permissions = new HashMap<String, List<Permission>>();
	}



	@Override
	public void addUserRole(String role) {
		if (!this.permissions.containsKey(role)) {
			this.permissions.put(role, new ArrayList<Permission>());
		}
	}

	@Override
	public void addUserRoles(List<String> roles) {
		for (String role : roles) {
			this.addUserRole(role);
		}
	}

	@Override
	public Boolean hasUserRole(String role) {
		return this.permissions.containsKey(role);
	}

	@Override
	public void checkPermission(String action, ArticleId articleId) throws PermissionDeniedException {

	}

	@Override
	public void checkPermission(Permission permission) throws PermissionDeniedException {
		if (this.permissions.isEmpty()) {
			throw new PermissionDeniedException("No roles found! Did you forget add role?");
		}
		if (!this.isAuthenticated) {
			throw new PermissionDeniedException("Not authenticated yet. Did you forget call authenticate?");
		}
		if (!this.isAllowed(permission)) {
			throw new PermissionDeniedException(
					"Permission denied for " + permission.getContext() +
					"::" + permission.getMethod() + " #" + permission.getArticleId()
			);
		}
	}

	@Override
	public Boolean isAllowed(Permission permission) {
		Boolean isAllowed = Boolean.FALSE;
		for (List<Permission> perms : this.permissions.values()) {
			if (perms.contains(permission)) {
				isAllowed = Boolean.TRUE;
				break;
			}
		}
		return isAllowed;
	}

	@Override
	public void setPermissionsSources(Class<?> aClass, IRoleDAO dao) {

	}

	@Override
	public void authenticate() {

	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}

}
