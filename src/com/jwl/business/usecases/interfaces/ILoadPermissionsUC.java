package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.Role;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface ILoadPermissionsUC {

	public Map<Role, List<AccessPermissions>> load(Set<Role> roles) throws ModelException;

}
