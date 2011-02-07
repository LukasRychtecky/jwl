package com.jwl.business.permissions;

import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.integration.dao.interfaces.IRoleDAO;

/**
 * 
 * @author Lukas Rychtecky
 */
public interface IIdentity {

	public void addUserRole(String role);

	public void addUserRoles(List<String> roles);

	public Boolean hasUserRole(String role);

	public void checkPermission(String action, ArticleId articleId)
			throws PermissionDeniedException;

	public void setPermissionsSources(Class<?> aClass, IRoleDAO dao);

	public void authenticate();

	public boolean isAuthenticated();

}
