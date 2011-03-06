package com.jwl.business.usecases;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.permissions.Permission;
import com.jwl.integration.IDAOFactory;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractUC {

	protected IDAOFactory factory;

	public AbstractUC(IDAOFactory factory) {
		this.factory = factory;
	}

	public void checkPermission(AccessPermissions action) throws PermissionDeniedException {
	}

	public void checkPermission(Permission permission) throws PermissionDeniedException {
		Environment.getIdentity().checkPermission(permission);
	}

}
