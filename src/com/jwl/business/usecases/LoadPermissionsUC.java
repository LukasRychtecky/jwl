package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.permissions.Permission;
import com.jwl.business.usecases.interfaces.ILoadPermissionsUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public class LoadPermissionsUC extends AbstractUC implements ILoadPermissionsUC {

	public LoadPermissionsUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public Map<String, List<AccessPermissions>> load(Set<String> roles) throws ModelException {
		Map<String, List<AccessPermissions>> permissions = null;

		try {
			permissions = super.factory.getRoleDAO().load(roles);
		} catch (DAOException e) {
			throw new ModelException(e);
		}

		return permissions;
	}

}
