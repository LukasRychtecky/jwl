package com.jwl.integration.role;

import com.jwl.business.security.AccessPermissions;
import com.jwl.business.security.Role;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * This class provides CRUD operations on RoleEntity entity.
 * 
 * @author Lukas Rychtecky
 */
public class RoleDAO extends BaseDAO implements IRoleDAO {

	private static final long serialVersionUID = -8198800235309610794L;
	private static final String FIND_ALL_WHERE = "SELECT r FROM RoleEntity r";
	private static final String DELETE_ROLE_HAS_PERMISSION = "DELETE FROM `role_has_permission`";
	private static final String FIND_ALL_PERMISSIONS = "PermissionEntity.findAll";
	private static final String FIND_ALL_ENTITIES = "RoleEntity.findAll";
	
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
			Query query = em.createQuery(this.buildQuery(RoleDAO.FIND_ALL_WHERE, roles.size(), "code"));

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
	private String buildQuery(String queryString, Integer count, String property) {
		StringBuilder query = new StringBuilder(queryString);

		for (int i = 0; i < count; i++) {
			query.append("r.").append(property).append(" = ?").append(i).append(" OR ");
		}
		// cut "OR" from end
		return query.substring(0, query.length() - 4);
	}

	@Override
	public Map<Role, List<AccessPermissions>> load(Set<Role> roles) throws DAOException {
		Map<Role, List<AccessPermissions>> permissions = new HashMap<Role, List<AccessPermissions>>();
		
		if (roles.isEmpty()) {
			return permissions;
		}
		
		EntityManager em = super.getEntityManager();
		try {
			Query query = em.createQuery(this.buildQuery(RoleDAO.FIND_ALL_WHERE + " WHERE ", roles.size(), "code"));

			Integer i = 0;
			for (Iterator<Role> it = roles.iterator(); it.hasNext();) {
				query.setParameter(i, it.next().getCode());
				i++;

			}
			@SuppressWarnings("unchecked")
			List<RoleEntity> result = (List<RoleEntity>) query.getResultList();

			for (RoleEntity entity : result) {
				permissions.put(RoleConvertor.toObject(entity), this.toObject(entity.getPermissionList()));
			}

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			super.closeEntityManager(em);
		}
		return permissions;
	}

	protected List<PermissionEntity> getAllPermissions(EntityManager em) {
		Query query = em.createNamedQuery(RoleDAO.FIND_ALL_PERMISSIONS);
		@SuppressWarnings("unchecked")
		List<PermissionEntity> perms = query.getResultList();
		return perms;
	}

	protected List<RoleEntity> getAllRoles(EntityManager em) {
		Query query = em.createNamedQuery(RoleDAO.FIND_ALL_ENTITIES);
		@SuppressWarnings("unchecked")
		List<RoleEntity> roles = query.getResultList();
		return roles;
	}

	private AccessPermissions toObject(PermissionEntity entity) {
		AccessPermissions perm = null;
		String name = entity.getContext() + "_" + entity.getMethod();
		try {
			perm = AccessPermissions.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			//TODO: skip unknow permission
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

	@Override
	public void save(Set<Role> roles) throws DAOException {

		UserTransaction ut = super.getUserTransaction();
		boolean localTrans = false;
		EntityManager em = super.getEntityManager();

		try {

			if (ut.getStatus() == Status.STATUS_NO_TRANSACTION) {
				ut.begin();
				localTrans = true;
			}
			em.joinTransaction();

			Query deleteFromRoleHasPermisson = em.createNativeQuery(RoleDAO.DELETE_ROLE_HAS_PERMISSION);
			deleteFromRoleHasPermisson.executeUpdate();

			List<PermissionEntity> existingPerms = this.getAllPermissions(em);
			List<RoleEntity> existingRoles = this.getAllRoles(em);

			for (Role role : roles) {
				RoleEntity roleEntity = RoleConvertor.toEntity(role);

				for (AccessPermissions accessPermissions : role.getPermissions()) {
					PermissionEntity newPerm = new PermissionEntity();
					newPerm.setContext(accessPermissions.getContext());
					newPerm.setMethod(accessPermissions.getMethod());
					PermissionEntity existingPerm = this.getExistPermission(existingPerms, newPerm);
					if (existingPerm != null) {
						existingPerm = em.merge(existingPerm);
					} else {
						em.persist(newPerm);
						existingPerm = newPerm;
					}

					roleEntity.addPermission(existingPerm);
					existingPerm.addRole(roleEntity);
					existingPerms.add(existingPerm);
				}

				Integer roleId = this.getExistingRoleId(existingRoles, roleEntity);
				if (roleId != null) {
					roleEntity.setId(roleId);
					roleEntity = em.merge(roleEntity);
					existingRoles.remove(roleEntity);
				}
				em.persist(roleEntity);
			}

			for (RoleEntity roleEntity : existingRoles) {
				em.remove(roleEntity);
			}

			em.flush();
			if (localTrans) {
				ut.commit();
			}

		} catch (Throwable e) {
			try {
				ut.rollback();
			} catch (Throwable t) {
				Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, t);
			}
			throw new DAOException(e);
		} finally {
			super.closeEntityManager(em);
		}
	}

	private PermissionEntity getExistPermission(List<PermissionEntity> perms, PermissionEntity entity) {
		for (PermissionEntity perm : perms) {
			if (perm.getContext().equals(entity.getContext()) && perm.getMethod().equals(entity.getMethod())) {
				return perm;
			}
		}
		return null;
	}

	private Integer getExistingRoleId(List<RoleEntity> roles, RoleEntity entity) {
		for (RoleEntity role : roles) {
			if (role.getCode().equals(entity.getCode())) {
				return new Integer(role.getId());
			}
		}
		return null;
	}
}
