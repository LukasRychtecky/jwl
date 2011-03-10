package com.jwl.business.usecases.interfaces;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.AccessPermissions;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lukas Rychtecky
 */
public interface ILoadPermissionsUC {

	public Map<String, List<AccessPermissions>> load(Set<String> roles) throws ModelException;

}
