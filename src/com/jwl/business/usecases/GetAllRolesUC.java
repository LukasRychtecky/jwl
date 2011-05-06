package com.jwl.business.usecases;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.Role;
import com.jwl.business.usecases.interfaces.IGetAllRolesUC;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.exceptions.DAOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Rychtecky
 */
public class GetAllRolesUC extends AbstractUC implements IGetAllRolesUC {

	public GetAllRolesUC(IDAOFactory factory) {
		super(factory);
	}

	@Override
	public Set<Role> get() throws ModelException {
		super.checkPermission(AccessPermissions.SECURITY_IMPORT);
		Set<Role> roles = new HashSet<Role>();
		try {
			roles = super.factory.getRoleDAO().getAll();
		} catch (DAOException ex) {
			throw new ModelException(ex);
		}
		
		return roles;
	}
	
}
