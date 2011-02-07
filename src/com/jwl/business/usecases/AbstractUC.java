package com.jwl.business.usecases;

import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.permissions.AccessPermissions;

/**
 *
 * @author Lukas Rychtecky
 */
abstract public class AbstractUC {

	public void checkPermission(AccessPermissions action) throws PermissionDeniedException {
	}

}
