package com.jwl.business.security;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.PermissionDeniedException;
import java.util.Set;

/**
 * 
 * @author Lukas Rychtecky
 */
public interface IIdentity {

	public Boolean hasUserRole(Role role);

	public Boolean isAllowed(AccessPermissions permission);

	public Boolean isAllowed(AccessPermissions permission, ArticleId articleId);
	
	public void checkPermission(AccessPermissions permission) throws PermissionDeniedException;

	public void checkPermission(AccessPermissions permission, ArticleId articleId) throws PermissionDeniedException;

	public boolean isAuthenticated();
	
	public String getUserName();
	
	public Set<Role> getRoles();

}
