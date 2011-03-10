package com.jwl.business.permissions;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.usecases.LoadPermissionsUC;
import com.jwl.business.usecases.interfaces.ILoadPermissionsUC;
import com.jwl.integration.IDAOFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lukas Rychtecky
 */
public class UserIdentity implements IIdentity {

	private IDAOFactory factory;
	private Map<String, List<AccessPermissions>> permissions = null;
	private Boolean isAuthenticated = Boolean.FALSE;
	private Boolean isPermissionsLoaded = Boolean.FALSE;

	public UserIdentity(IDAOFactory factory) {
		this.factory = factory;
		this.permissions = new HashMap<String, List<AccessPermissions>>();
	}

	private void loadPermissions() throws ModelException {
		if (!this.isPermissionsLoaded && !this.permissions.keySet().isEmpty()) {
			ILoadPermissionsUC uc = new LoadPermissionsUC(this.factory);
			this.permissions = uc.load(this.permissions.keySet());
			this.isPermissionsLoaded = Boolean.TRUE;
			this.isAuthenticated = Boolean.TRUE;
		}
	}

	@Override
	public void addUserRole(String role) {
		if (!this.permissions.containsKey(role)) {
			this.permissions.put(role, new ArrayList<AccessPermissions>());
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
		//TODO: exclude role

		return isAllowed;
	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}
}
