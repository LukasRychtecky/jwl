package com.jwl.business.usecases;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.security.AccessPermissions;
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

	public void checkPermission(AccessPermissions permission) throws ModelException, PermissionDeniedException {
		Environment.getIdentity().checkPermission(permission);
	}

	public void checkPermission(AccessPermissions permission, ArticleId id) throws ModelException, PermissionDeniedException {
		Environment.getIdentity().checkPermission(permission, id);
	}

	public boolean isAllowed(AccessPermissions permission) throws ModelException {
		return Environment.getIdentity().isAllowed(permission);
	}

	public boolean isAllowed(AccessPermissions permission, ArticleId id) throws ModelException {
		return Environment.getIdentity().isAllowed(permission, id);
	}

}
