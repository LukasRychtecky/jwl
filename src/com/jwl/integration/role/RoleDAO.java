package com.jwl.integration.role;

import com.jwl.business.permissions.AccessPermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jwl.integration.BaseDAO;
import com.jwl.integration.exceptions.DAOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class provides CRUD operations on Role entity.
 * 
 * @author Lukas Rychtecky
 */
public class RoleDAO extends BaseDAO implements IRoleDAO {

	private static final long serialVersionUID = -8198800235309610794L;
	private static final String FIND_ALL_WHERE = "SELECT r FROM Role r WHERE ";

	/**
	 * Returns roles by given names.
	 * 
	 * @param roles
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> findRoles(List<String> roles) throws DAOException {
		List<Object> list = new ArrayList<Object>();
		EntityManager em = getEntityManager();
		try {
			Query query = em.createQuery(this.buildQuery(roles.size()));

			for (int i = 0; i < roles.size(); i++) {
				query.setParameter(i, roles.get(i));
			}

			list = (List<Object>) query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			closeEntityManager(em);
		}
		return list;
	}

	/**
	 * Builds query with conditions by given count of roles
	 * 
	 * @param count
	 * @return
	 */
	private String buildQuery(Integer count) {
		StringBuilder query = new StringBuilder(RoleDAO.FIND_ALL_WHERE);

		for (int i = 0; i < count; i++) {
			query.append("r.code = ?").append(i).append(" OR ");
		}
		// cut "OR" from end
		return query.substring(0, query.length() - 4);
	}

	@Override
	public Map<String, List<AccessPermissions>> load(Set<String> roles) throws DAOException {
		Map<String, List<AccessPermissions>> permissions = new HashMap<String, List<AccessPermissions>>();
		EntityManager em = super.getEntityManager();
		try {
			Query query = em.createQuery(this.buildQuery(roles.size()));

			Integer i = 0;
			for (Iterator<String> it = roles.iterator(); it.hasNext();) {
				query.setParameter(i, it.next());
				i++;

			}
			@SuppressWarnings("unchecked")
			List<Role> result = (List<Role>) query.getResultList();

			for (Role role : result) {
				permissions.put(role.getCode(), this.toObject(role.getPermissionList()));
			}

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			super.closeEntityManager(em);
		}
		return permissions;
	}

	private AccessPermissions toObject(PermissionEntity entity) {
		AccessPermissions perm = null;
		String name = entity.getContext() + "_" + entity.getMethod();
		try {
			perm = AccessPermissions.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			//TODO: process ex.
		}

		return perm;
	}

	private List<AccessPermissions> toObject(List<PermissionEntity> entities) {
		List<AccessPermissions> perms = new ArrayList<AccessPermissions>();
		for (PermissionEntity entity : entities) {
			AccessPermissions perm = this.toObject(entity);
			if (perm != null) {
				perms.add(perm);
			}
		}
		return perms;
	}
}
