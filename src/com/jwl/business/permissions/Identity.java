package com.jwl.business.permissions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.NoAuthenticatedYetException;
import com.jwl.business.exceptions.NoRoleFoundException;
import com.jwl.business.exceptions.PermissionDeniedException;
import com.jwl.business.exceptions.UnexpectedActionException;
import com.jwl.integration.entity.Article;
import com.jwl.integration.entity.RoleOld;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.role.IRoleDAO;

/**
 * This class represents user of application.
 *
 * @author Lukas Rychtecky
 */
public class Identity implements IIdentity {

	private List<String> roles = null;
	private PermissionStore permissions=null;
	private IRoleDAO dao = null;
	private Boolean isAuthenticated = Boolean.FALSE;
	private Class<?> userActionsSource;

	public Identity() {
		this.permissions = new PermissionStore();
		this.roles = new ArrayList<String>();
	}
	
	/**
	 * Adds new role.
	 *
	 * @param role
	 */
	@Override
	public void addUserRole(String role) {
		if (role.length() > 0) {
			this.roles.add(role);
		}
	}

	/**
	 * Adds new roles
	 *
	 * @param roles
	 */
	@Override
	public void addUserRoles(List<String> roles) {
		for (String role : roles) {
			this.addUserRole(role);
		}
	}

	/**
	 * If user has role returns true, otherwise false.
	 *
	 * @param role
	 * @return
	 */
	@Override
	public Boolean hasUserRole(String role) {
		return this.roles.contains(role);
	}

	/**
	 * If user has permission do nothing, otherwise throw exception.
	 *
	 * @param action
	 * @throws PermissionDeniedException
	 * @throws UnexpectedActionException
	 * @throws NoRoleFoundException
	 */
	@Override
	public void checkPermission(String action, ArticleId articleId)
			throws PermissionDeniedException {
		
		try {
			if (!this.hasPermission(action, articleId)) {
				throw new PermissionDeniedException(
						"Permission denied for action: " + action);
			}
		} catch (UnexpectedActionException e) {
			throw new PermissionDeniedException(e);
		} catch (NoRoleFoundException e) {
			throw new PermissionDeniedException(e);
		} catch (NoAuthenticatedYetException e) {
			throw new PermissionDeniedException(e);
		} catch (DAOException e) {
			throw new PermissionDeniedException(e);
		}
	}

	/**
	 * If user has permission for given action returns true, otherwise false.
	 * 
	 * @param actionNoAuthenticatedYetException 
	 * @return
	 * @throws DAOException 
	 */
	private Boolean hasPermission(String action, ArticleId articleId)
			throws UnexpectedActionException, NoRoleFoundException,
			NoAuthenticatedYetException, DAOException {

		if (this.roles.isEmpty()) {
			throw new NoRoleFoundException("Did you forget add role?");
		}
		if (!this.isAuthenticated) {
			throw new NoAuthenticatedYetException("Did you forget call authenticate?");
		}
		if (!this.permissions.containsAction(action)) {
			throw new UnexpectedActionException("Unexpected action: " + action);
		}
		
		Set<Integer> excludedRolesId = this.getExcludedRolesId(articleId);
		return this.permissions.hasPermission(action, excludedRolesId);
	}
	

	private Set<Integer> getExcludedRolesId(ArticleId articleId) throws DAOException{
		List<Object> allRoles = this.dao.findRoles(this.roles);
		Set<Integer> excludedRoles = new HashSet<Integer>();
		for (Object roleObject : allRoles) {
			RoleOld role = (RoleOld)roleObject;
			for (Article article : role.getArticles()) {
				if (article.getId().equals(articleId.getId())) {
					excludedRoles.add(role.getId());
				}
			}
		}
		return excludedRoles;
	}
	
	/**
	 * Loads permissions of "RoleOld" entity from Data Access Object
	 *
	 * @param classObject
	 * @param dao
	 */
	@Override
	public void setPermissionsSources(Class<?> classObject, IRoleDAO dao) {
		this.userActionsSource = classObject;
		this.dao = dao;
	}
	
	/**
	 * Authenticates user.
	 */
	@Override
	public void authenticate() {
		this.isAuthenticated = Boolean.TRUE;
		this.loadPermissions();
	}
	
	/**
	 * Loads role's permissions values from storage
	 *
	 * @return
	 */
	private void loadPermissions() {
		try {
			List<Object> foundRoles = this.dao.findRoles(this.roles);
			createPermissions(foundRoles);			
		} catch (IllegalAccessException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		} catch (IllegalArgumentException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		} catch (InvocationTargetException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		} catch (NoSuchMethodException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		} catch (SecurityException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		} catch (DAOException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	
	/**
	 * Reflexively process given permissions
	 *  Reflexively loads permissions names from entity
	 *
	 * @param roleObjects
	 */
	private void createPermissions(List<Object> roleObjects)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		
		List<String> actions = getUserActions();
		this.permissions.setActions(actions);
		
		for (Object roleObject : roleObjects) {
			RoleOld role = (RoleOld) roleObject;
			for (String action : actions) {
				Method method = role.getClass().getMethod("is" + action);
				Boolean permission = false || (Boolean) method.invoke(role);
				this.permissions.addPermision(role.getId(), action, permission);
			}
		}
	}

	private List<String> getUserActions() {
		List<String> actions = new ArrayList<String>();
		for (Method method : userActionsSource.getMethods()) {
			String methodName = method.getName();
			if (methodName.startsWith("is")) {
				actions.add(methodName.substring(2));
			}
		}
		return actions;
	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}

	@Override
	public Boolean isAllowed(Permission permission) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void checkPermission(Permission permission) throws PermissionDeniedException {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	private class PermissionStore {
		List<String> actions;
		Map<Integer, Map<String, Boolean>> ps;
		
		public PermissionStore(){
			ps = new HashMap<Integer, Map<String, Boolean>>();
			actions = new ArrayList<String>();
		}


		public void addPermision(Integer roleId, String action, Boolean permit) {	
			if (!ps.containsKey(roleId)) {
				ps.put(roleId, new HashMap<String, Boolean>());
			}
			Map<String, Boolean> roleActions = ps.get(roleId);	
			roleActions.put(action, permit);
		}
		
		public boolean hasPermission(String action, Set<Integer> excludedRoles){
			for (Integer roleId : ps.keySet()) {
				if(excludedRoles.contains(roleId)){
					continue;
				} else if (ps.get(roleId).get(action) == true) {
					return true;
				}
			}
			return false;
		}
		
		public void setActions(List<String> actions) {
			this.actions = actions; 
		}

		public boolean containsAction(String aciton) {
			return actions.contains(aciton);
		}
	}
	
}
