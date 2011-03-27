package com.jwl.business.security;

import com.jwl.business.security.AccessPermissions;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.usecases.LoadPermissionsUC;
import com.jwl.business.usecases.interfaces.ILoadPermissionsUC;
import com.jwl.integration.IDAOFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class UserIdentity implements IIdentity {

	private IDAOFactory factory;
	private Map<Role, List<AccessPermissions>> permissions = null;
	private Boolean isAuthenticated = Boolean.FALSE;
	private Boolean isPermissionsLoaded = Boolean.FALSE;
	private Set<Role> roles;
	private String userName = null;

	public UserIdentity(IDAOFactory factory) {
		this.factory = factory;
		this.permissions = new HashMap<Role, List<AccessPermissions>>();
		this.roles = new HashSet<Role>();
	}

	private void loadPermissions() throws ModelException {
		if (!this.isPermissionsLoaded && !this.roles.isEmpty()) {
			ILoadPermissionsUC uc = new LoadPermissionsUC(this.factory);
			this.permissions = uc.load(this.roles);
			this.isPermissionsLoaded = Boolean.TRUE;
			this.isAuthenticated = Boolean.TRUE;
		}
	}

	@Override
	public void addUserRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public void addUserRoles(List<Role> roles) {
		for (Role role : roles) {
			this.addUserRole(role);
		}
	}

	@Override
	public Boolean hasUserRole(Role role) {
		return this.permissions.containsKey(role);
	}

	@Override
	public void checkPermission(AccessPermissions permission) throws ModelException, PermissionDeniedException {
		this.loadPermissions();
		if (this.permissions.keySet().isEmpty()) {
			throw new PermissionDeniedException("No roles found! Did you forget add role?");
		}
		if (!this.isAllowed(permission)) {
			throw new PermissionDeniedException("Permission denied for " + permission);
		}
	}

	@Override
	public void checkPermission(AccessPermissions permission, ArticleId articleId) throws ModelException, PermissionDeniedException {
		this.checkPermission(permission);
		if (!this.isAllowed(permission, articleId)) {
			throw new PermissionDeniedException("Permission denied for article #" + articleId);
		}
	}

	@Override
	public Boolean isAllowed(AccessPermissions permission) throws ModelException {
		this.loadPermissions();
		Boolean isAllowed = Boolean.FALSE;

		for (List<AccessPermissions> perms : this.permissions.values()) {
			if (perms.contains(permission)) {
				isAllowed = Boolean.TRUE;
				break;
			}
		}
		return isAllowed;
	}

	@Override
	public Boolean isAllowed(AccessPermissions permission, ArticleId id) throws ModelException {
		Boolean isAllowed = this.isAllowed(permission);

		if (!isAllowed) {
			return isAllowed;
		}

		for (Role role : this.permissions.keySet()) {
			if (role.getArticlesId().contains(id)) {
				isAllowed = Boolean.FALSE;
				break;
			}
		}

		return isAllowed;
	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}

	@Override
	public void addUserName(String name) {
		this.userName = name;
		
	}

	@Override
	public String getUserName() {
		return userName;
	}
}
