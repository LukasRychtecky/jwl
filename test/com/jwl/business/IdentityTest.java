package com.jwl.business;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jwl.business.permissions.IIdentity;
import com.jwl.business.permissions.Identity;
import com.jwl.integration.role.IRoleDAO;

/**
 *
 * @author lukas
 */
public class IdentityTest extends TestCase {

	private String admin = "administrator";
	private String editor = "editor";
	private String slave = "slave";
	private List<String> roles;
	private List<String> permissions;
	private IRoleDAO dao;
	private IRoleDAO emptyDAO;

	public IdentityTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	@Override
	public void setUp() {
		this.roles = new ArrayList<String>();
		this.roles.add(this.editor);
		this.roles.add(this.admin);
		this.roles.add(this.slave);
		this.dao = new RoleDAOMock();
		this.emptyDAO = new RoleEmptyDAOMock();

		this.permissions = new ArrayList<String>();
		Method[] methods = RoleMock.class.getMethods();
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.startsWith("is")) {
				String action = methodName.substring(2, methodName.length());
				this.permissions.add(action);
			}

		}
	}

	@After
	@Override
	public void tearDown() {
	}

	/**
	 * Test of hasRole method, of class Identity.
	 */
	@Test
	public void testHasRoleTrue() {
		Identity identity = new Identity();
		identity.addUserRole(this.admin);
		IdentityTest.assertTrue(identity.hasUserRole(this.admin));
	}

	/**
	 * Test of hasRole method, of class Identity.
	 */
	@Test
	public void testHasRoleFalse() {
		Identity identity = new Identity();
		identity.addUserRole("");
		IdentityTest.assertFalse(identity.hasUserRole(this.admin));
		identity.addUserRole(this.editor);
		IdentityTest.assertFalse(identity.hasUserRole(this.admin));
	}

	/**
	 * Test of hasRole method, of class Identity.
	 */
	@Test
	public void testHasRoleList() {
		Identity identity = new Identity();
		identity.addUserRoles(this.roles);

		IdentityTest.assertFalse(identity.hasUserRole("hacker"));
		IdentityTest.assertTrue(identity.hasUserRole(this.admin));
		IdentityTest.assertTrue(identity.hasUserRole(this.editor));
		IdentityTest.assertTrue(identity.hasUserRole(this.slave));
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionNoRole() {
		IIdentity identity = new Identity();

		for (String action : this.permissions) {
//			try {
//				IdentityTest.assertTrue(identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				return;
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			IdentityTest.fail("No role added.");
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionEmptyDAO() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMock.class, this.emptyDAO);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				IdentityTest.assertFalse(identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added.");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionEmptyDAO() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMock.class, this.emptyDAO);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added.");
//			} catch (PermissionDeniedException ex) {
//				continue;
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			IdentityTest.fail("Expected exception: PermissionDeniedException");
		}
	}/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionNoAuthenticated() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMock.class, this.emptyDAO);

		for (String action : this.permissions) {
//			try {
//				identity.hasPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added.");
//			} catch (NoAuthenticatedYetException e) {
//				return;
//			}
			IdentityTest.fail("Expected exception: NoAuthenticatedYetException");
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionNoAuthenticated() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMock.class, this.emptyDAO);

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added.");
//			} catch (PermissionDeniedException ex) {
//				continue;
//			} catch (NoAuthenticatedYetException e) {
//				return;
//			}
			IdentityTest.fail("Expected exception: NoAuthenticatedYetException");
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionNoPermissionLoaded() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMockExmpty.class, this.emptyDAO);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.hasPermission(action);
//			} catch (UnexpectedActionException e) {
//				continue;
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added.");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			IdentityTest.fail("Expected exception: UnexpectedActionException");
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionNoPermissionLoaded() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMockExmpty.class, this.emptyDAO);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				continue;
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added.");
//			} catch (PermissionDeniedException e) {
//				IdentityTest.fail("Expected exception: UnexpectedActionException");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			IdentityTest.fail("Expected exception: UnexpectedActionException");
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionAdmin() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMock.class, this.dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				IdentityTest.assertTrue(identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionAdmin() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (PermissionDeniedException e) {
//				IdentityTest.fail("Permission denied for action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionSlave() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.slave);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				IdentityTest.assertFalse(identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionSlave() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.slave);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (PermissionDeniedException e) {
//				continue;
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			IdentityTest.fail("Expected exception: PermissionDeniedException");
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionEditor() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.editor);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		Map<String, Boolean> editorPermissions = this.editorPermissionProvider();

		for (String action : editorPermissions.keySet()) {

//			try {
//				IdentityTest.assertEquals(editorPermissions.get(action), identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionEditor() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.editor);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		Map<String, Boolean> editorPermissions = this.editorPermissionProvider();

		for (String action : editorPermissions.keySet()) {

//			try {
//
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (PermissionDeniedException e) {
//				if (editorPermissions.get(action)) {
//					IdentityTest.fail("Invalid permission denied.");
//				}
//				continue;
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			if (!editorPermissions.get(action)) {
				IdentityTest.fail("Expected exception: PermissionDeniedException");
			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionEditorAndSLave() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.editor);
		identity.addUserRole(this.slave);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		Map<String, Boolean> editorPermissions = this.editorPermissionProvider();

		for (String action : editorPermissions.keySet()) {

//			try {
//				IdentityTest.assertEquals(editorPermissions.get(action), identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionEditorAndSlave() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.editor);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		Map<String, Boolean> editorPermissions = this.editorPermissionProvider();

		for (String action : editorPermissions.keySet()) {

//			try {
//
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (PermissionDeniedException e) {
//				if (editorPermissions.get(action)) {
//					IdentityTest.fail("Invalid permission denied.");
//				}
//				continue;
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
			if (!editorPermissions.get(action)) {
				IdentityTest.fail("Expected exception: PermissionDeniedException");
			}
		}
	}

	private Map<String, Boolean> editorPermissionProvider() {
		Map<String, Boolean> editorPermissions = new HashMap<String, Boolean>();
		editorPermissions.put("ArticleView", Boolean.TRUE);
		editorPermissions.put("ArticleEdit", Boolean.TRUE);
		editorPermissions.put("ArticleRename", Boolean.FALSE);
		editorPermissions.put("ArticleRestore", Boolean.FALSE);
		editorPermissions.put("ArticleLock", Boolean.FALSE);
		editorPermissions.put("ArticleDelete", Boolean.FALSE);
		editorPermissions.put("ArticleExcludeRole", Boolean.FALSE);
		editorPermissions.put("AttachmentView", Boolean.TRUE);
		editorPermissions.put("AttachmentAdd", Boolean.TRUE);
		editorPermissions.put("AttachmentDelete", Boolean.FALSE);
		return editorPermissions;
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionAdminAndSlave() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.addUserRole(this.slave);
		identity.setPermissionsSources(RoleMock.class, this.dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				IdentityTest.assertTrue(identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionAdminAndSlave() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.addUserRole(this.slave);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (PermissionDeniedException e) {
//				IdentityTest.fail("Permission denied for action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testHasPermissionAdminAndSlaveAndEditor() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.addUserRole(this.slave);
		identity.addUserRole(this.editor);
		identity.setPermissionsSources(RoleMock.class, this.dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				IdentityTest.assertTrue(identity.hasPermission(action));
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	/**
	 * Test of hasPermission method, of class Identity.
	 */
	@Test
	public void testCheckPermissionAdminAndSlaveAndEditor() {
		IIdentity identity = new Identity();
		identity.addUserRole(this.admin);
		identity.addUserRole(this.slave);
		identity.addUserRole(this.editor);
		identity.setPermissionsSources(RoleMock.class, dao);
		identity.authenticate();

		for (String action : this.permissions) {
//			try {
//				identity.checkPermission(action);
//			} catch (UnexpectedActionException e) {
//				IdentityTest.fail("Unexpected action: " + action);
//			} catch (PermissionDeniedException e) {
//				IdentityTest.fail("Permission denied for action: " + action);
//			} catch (NoRoleFoundException e) {
//				IdentityTest.fail("No role added");
//			} catch (NoAuthenticatedYetException e) {
//				IdentityTest.fail("Unexpected exception." + e.getMessage());
//			}
		}
	}

	private class RoleEmptyDAOMock implements IRoleDAO {

		@Override
		public List<Object> findRoles(List<String> roles) {
			List<Object> emptyList = new ArrayList<Object>();
			return emptyList;
		}

	}

	private class RoleDAOMock implements IRoleDAO {

		@Override
		public List<Object> findRoles(List<String> roles) {
			ArrayList<Object> result = new ArrayList<Object>();

			Object[] permission = new Boolean[1];

			if (roles.contains("administrator")) {
				RoleMock admin = new RoleMock();
				admin.setName("administrator");
				permission[0] = Boolean.TRUE;
				admin = this.setPermissions(admin, permission);
				result.add(admin);
			}

			if (roles.contains("slave")) {
				RoleMock slave = new RoleMock();
				slave.setName("slave");
				permission[0] = Boolean.FALSE;
				slave = this.setPermissions(slave, permission);
				result.add(slave);
			}

			if (roles.contains("editor")) {
				RoleMock editor = new RoleMock();
				editor.setName("editor");
				editor.setArticleDelete(false);
				editor.setArticleEdit(true);
				editor.setArticleExcludeRole(false);
				editor.setArticleLock(false);
				editor.setArticleRename(false);
				editor.setArticleRestore(false);
				editor.setArticleView(true);
				editor.setAttachmentAdd(true);
				editor.setAttachmentDelete(false);
				editor.setAttachmentView(true);

				result.add(editor);
			}

			return result;
		}

		private RoleMock setPermissions(RoleMock role, Object[] permission) {
			Method[] methods = RoleMock.class.getMethods();
			StringBuilder methodName = null;
			StringBuilder callMethodName = null;
			Method method = null;
			Class<?>[] parameterTypes = {Boolean.TYPE};

			for (int i = 0; i < methods.length; i++) {
				methodName = new StringBuilder(methods[i].getName());

				if (methodName.toString().startsWith("is")) {
					try {
						callMethodName = new StringBuilder("set" + methodName.substring(2, methodName.length()));
						method = RoleMock.class.getMethod(callMethodName.toString(), parameterTypes);
						method.invoke(role, permission);
					} catch (NoSuchMethodException ex) {
						Logger.getLogger(IdentityTest.class.getName()).log(Level.SEVERE, null, ex);
					} catch (SecurityException ex) {
						Logger.getLogger(IdentityTest.class.getName()).log(Level.SEVERE, null, ex);
					} catch (IllegalAccessException ex) {
						Logger.getLogger(IdentityTest.class.getName()).log(Level.SEVERE, null, ex);
					} catch (IllegalArgumentException ex) {
						Logger.getLogger(IdentityTest.class.getName()).log(Level.SEVERE, null, ex);
					} catch (InvocationTargetException ex) {
						Logger.getLogger(IdentityTest.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}

			return role;
		}
	}

	private class RoleMock extends Object {

		private String name;
		private boolean articleView;
		private boolean articleEdit;
		private boolean articleRename;
		private boolean articleRestore;
		private boolean articleLock;
		private boolean articleDelete;
		private boolean articleExcludeRole;
		private boolean attachmentView;
		private boolean attachmentAdd;
		private boolean attachmentDelete;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isArticleView() {
			return this.articleView;
		}

		public void setArticleView(boolean articleView) {
			this.articleView = articleView;
		}

		public boolean isArticleEdit() {
			return this.articleEdit;
		}

		public void setArticleEdit(boolean articleEdit) {
			this.articleEdit = articleEdit;
		}

		public boolean isArticleRename() {
			return this.articleRename;
		}

		public void setArticleRename(boolean articleRename) {
			this.articleRename = articleRename;
		}

		public boolean isArticleRestore() {
			return this.articleRestore;
		}

		public void setArticleRestore(boolean articleRestore) {
			this.articleRestore = articleRestore;
		}

		public boolean isArticleLock() {
			return this.articleLock;
		}

		public void setArticleLock(boolean articleLock) {
			this.articleLock = articleLock;
		}

		public boolean isArticleDelete() {
			return this.articleDelete;
		}

		public void setArticleDelete(boolean articleDelete) {
			this.articleDelete = articleDelete;
		}

		public boolean isArticleExcludeRole() {
			return this.articleExcludeRole;
		}

		public void setArticleExcludeRole(boolean articleExcludeRole) {
			this.articleExcludeRole = articleExcludeRole;
		}

		public boolean isAttachmentView() {
			return this.attachmentView;
		}

		public void setAttachmentView(boolean attachmentView) {
			this.attachmentView = attachmentView;
		}

		public boolean isAttachmentAdd() {
			return this.attachmentAdd;
		}

		public void setAttachmentAdd(boolean attachmentAdd) {
			this.attachmentAdd = attachmentAdd;
		}

		public boolean isAttachmentDelete() {
			return this.attachmentDelete;
		}

		public void setAttachmentDelete(boolean attachmentDelete) {
			this.attachmentDelete = attachmentDelete;
		}
	}

	private class RoleMockExmpty {

	}
}
