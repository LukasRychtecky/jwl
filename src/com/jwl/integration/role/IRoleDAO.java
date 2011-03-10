package com.jwl.integration.role;

import com.jwl.business.permissions.AccessPermissions;
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

	public Map<String, List<AccessPermissions>> load(Set<String> roles) throws DAOException;

}
