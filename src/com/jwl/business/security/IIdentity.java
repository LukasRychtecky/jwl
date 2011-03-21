package com.jwl.business.security;

import com.jwl.business.security.Role;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.article.ArticleId;
import java.util.List;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.PermissionDeniedException;

/**
 * 
 * @author Lukas Rychtecky
 */
public interface IIdentity {

	public void addUserRole(Role role);

	public void addUserRoles(List<Role> roles);

	public Boolean hasUserRole(Role role);

	public Boolean isAllowed(AccessPermissions permission) throws ModelException;

	public Boolean isAllowed(AccessPermissions permission, ArticleId articleId) throws ModelException;
	
	public void checkPermission(AccessPermissions permission) throws ModelException, PermissionDeniedException;

	public void checkPermission(AccessPermissions permission, ArticleId articleId) throws ModelException, PermissionDeniedException;

	public boolean isAuthenticated();

}
