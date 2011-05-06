package com.jwl.integration.role;

import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.Role;
import com.jwl.integration.exceptions.DAOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IRoleDAO {

	public List<Object> findRoles(List<String> roles) throws DAOException;

	public Map<Role, List<AccessPermissions>> load(Set<Role> roles) throws DAOException;

	public void save(Set<Role> roles) throws DAOException;
	
	public Set<Role> getAll() throws DAOException;

}
