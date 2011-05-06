package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.Role;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface IGetAllRolesUC {
	
	public Set<Role> get() throws ModelException;
	
}
