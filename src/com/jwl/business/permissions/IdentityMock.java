package com.jwl.business.permissions;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.integration.role.IRoleDAO;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public class IdentityMock implements IIdentity {

	@Override
	public void addUserRole(String role) {

	}

	@Override
	public void addUserRoles(List<String> roles) {

	}

	@Override
	public Boolean hasUserRole(String role) {
		return Boolean.TRUE;
	}

	@Override
	public void checkPermission(String action, ArticleId articleId) throws PermissionDeniedException {

	}

	@Override
	public void setPermissionsSources(Class<?> aClass, IRoleDAO dao) {

	}

	@Override
	public void authenticate() {

	}

	@Override
	public boolean isAuthenticated() {
		return Boolean.TRUE;
	}

}
