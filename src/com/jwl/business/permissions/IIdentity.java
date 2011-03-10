package com.jwl.business.permissions;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.PermissionDeniedException;

/**
 * 
 * @author Lukas Rychtecky
 */
public interface IIdentity {

	public void addUserRole(String role);

	public void addUserRoles(List<String> roles);

	public Boolean hasUserRole(String role);

	public Boolean isAllowed(AccessPermissions permission) throws ModelException;

	public Boolean isAllowed(AccessPermissions permission, ArticleId id) throws ModelException;
	
	public void checkPermission(AccessPermissions permission) throws ModelException, PermissionDeniedException;

	public void checkPermission(AccessPermissions permission, ArticleId articleId) throws ModelException, PermissionDeniedException;

	public boolean isAuthenticated();

}
