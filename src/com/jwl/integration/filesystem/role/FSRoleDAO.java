package com.jwl.integration.filesystem.role;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jwl.business.permissions.AccessPermissions;
import com.jwl.business.permissions.Role;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.role.IRoleDAO;

public class FSRoleDAO implements IRoleDAO {

	@Override
	public List<Object> findRoles(List<String> roles) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Role, List<AccessPermissions>> load(Set<Role> roles)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
