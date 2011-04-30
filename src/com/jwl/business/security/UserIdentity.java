package com.jwl.business.security;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.usecases.LoadPermissionsUC;
import com.jwl.business.usecases.interfaces.ILoadPermissionsUC;
import com.jwl.integration.IDAOFactory;
import java.util.Collections;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lukas Rychtecky
 */
public class UserIdentity implements IIdentity {

	private static final String PRINCIPAL_KEY = "jwl-principal";
	
	private IDAOFactory factory;
	private Map<Role, List<AccessPermissions>> permissions = null;
	private Boolean isAuthenticated = Boolean.FALSE;
	private Principal principal = null;
	private Map<String, Object> principalStorage;

	public UserIdentity(String username, Set<Role> roles, IDAOFactory factory, Map<String, Object> principalStorage) throws ModelException {
		this.factory = factory;
		this.permissions = new HashMap<Role, List<AccessPermissions>>();
		this.principalStorage = principalStorage;
		this.loadPrincipal(username, roles);
		this.loadPermissions();
	}
	
	private void loadPrincipal(String username, Set<Role> roles) {
		this.principal = (Principal) this.principalStorage.get(PRINCIPAL_KEY);
		if (this.principal == null) {
			this.principal = new Principal(username, roles);
			this.principalStorage.put(PRINCIPAL_KEY, this.principal);
		}
	}

	private void loadPermissions() throws ModelException {
		ILoadPermissionsUC uc = new LoadPermissionsUC(this.factory);
		this.permissions = uc.load(this.principal.getRoles());
		this.isAuthenticated = Boolean.TRUE;
	}

	@Override
	public Boolean hasUserRole(Role role) {
		return this.principal.getRoles().contains(role);
	}

	@Override
	public void checkPermission(AccessPermissions permission) throws PermissionDeniedException {
		if (this.permissions.keySet().isEmpty()) {
			throw new PermissionDeniedException("No roles found! Did you forget add role?");
		}
		if (!this.isAllowed(permission)) {
			throw new PermissionDeniedException("Permission denied for " + permission);
		}
	}

	@Override
	public void checkPermission(AccessPermissions permission, ArticleId articleId) throws PermissionDeniedException {
		if (!this.isAllowed(permission, articleId)) {
			throw new PermissionDeniedException("Permission denied for article #" + articleId);
		}
	}

	@Override
	public Boolean isAllowed(AccessPermissions permission) {
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
	public Boolean isAllowed(AccessPermissions permission, ArticleId id) {
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
	public String getUserName() {
		return this.principal.getUsername();
	}
	
	@Override
	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(this.principal.getRoles());
	}
}
