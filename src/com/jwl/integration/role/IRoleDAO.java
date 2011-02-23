package com.jwl.integration.role;

import com.jwl.integration.exceptions.DAOException;
import java.util.List;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IRoleDAO {

	public List<Object> findRoles(List<String> roles) throws DAOException;

}
