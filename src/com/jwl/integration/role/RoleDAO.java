package com.jwl.integration.role;

import com.jwl.integration.dao.EntityManagerDAO;
import com.jwl.integration.exceptions.DAOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * This class provides CRUD operations on Role entity.
 *
 * @author Lukas Rychtecky
 */
public class RoleDAO extends EntityManagerDAO implements IRoleDAO {
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
		try {
			EntityManager manager = this.getEntityManagerFactory().createEntityManager();
			Query query = manager.createQuery(this.buildQuery(roles.size()));

			for (int i = 0; i < roles.size(); i++) {
				query.setParameter(i, roles.get(i));
			}

			list = (List<Object>) query.getResultList();
		} catch (Exception e) {
			throw new DAOException(e);
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
			query.append("r.name = ?").append(i).append(" OR ");
		}
		// cut "OR" from end
		return query.substring(0, query.length() - 4);
	}


}
