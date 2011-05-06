package com.jwl.business;

import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.KnowledgeManagementFacade;
import com.jwl.business.security.IIdentity;
import com.jwl.business.security.UserIdentity;

import com.jwl.business.knowledge.util.ISettings;
import com.jwl.business.knowledge.util.SettingsSource;
import com.jwl.business.security.Role;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.JPADAOFactory;
import com.jwl.integration.filesystem.FSDAOFactory;
import java.io.File;
import java.util.Set;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Lukas Rychtecky
 */
public class Environment {

	private static JWLConfig config = null;
	private static String JWL_HOME = null;
	private static IDAOFactory factory = null;
	private static IIdentity identity = null;
	private static ISettings knowledgeSettings = null;
	private static IKnowledgeManagementFacade knowledgeFacade = null;

	private Environment() {
	}

	public static String getPersistenceUnit() {
		return config.getPersistanceUnit();
	}

	public static void setPersistenceUnit(String persistenceUnit) {
		if (persistenceUnit != null && !persistenceUnit.isEmpty()) {
			config.setPersistanceUnit(persistenceUnit);
		}
	}

	public static IDAOFactory getDAOFactory() {
		if (Environment.factory == null) {
			if (config.getPersistanceUnit() == null ? JWLConfig.FILESYSTEM_PU == null
					: config.getPersistanceUnit()
							.equals(JWLConfig.FILESYSTEM_PU)) {
				Environment.factory = new FSDAOFactory();
			} else {
				Environment.factory = new JPADAOFactory();
			}
		}
		return Environment.factory;
	}

	public static String getJWLHome() {
		return Environment.JWL_HOME;
	}

	public static void setJWLHome(String jwlHome) {
		if (Environment.JWL_HOME == null) {
			Environment.JWL_HOME = jwlHome;
			config = new JWLConfig(Environment.JWL_HOME + File.separator + "private"
					+ File.separator + "config" + File.separator
					+ JWLConfig.CONFIG_FILE_NAME);
		}
	}

	public static String getACLFileName() {
		return Environment.JWL_HOME + File.separator + "private"
				+ File.separator + "tmp" + File.separator
				+ config.getAclFileName();
	}

	public static String getAttachmentStorage() {
		return Environment.JWL_HOME + File.separator + "private"
				+ File.separator + "jwl-files";
	}

	public static IIdentity getIdentity() {
		return Environment.identity;
	}

	public static IIdentity createIdentity(String username, Set<Role> roles)
			throws ModelException {
		Environment.identity = new UserIdentity(username, roles,
				getDAOFactory(), getPermissionStorage());
		return Environment.identity;
	}

	public static ISettings getKnowledgeSettings() {
		if (knowledgeSettings == null) {
			knowledgeSettings = new SettingsSource(JWL_HOME);
		}
		return knowledgeSettings;
	}

	public static IKnowledgeManagementFacade getKnowledgeFacade() {
		if (knowledgeFacade == null) {
			knowledgeFacade = new KnowledgeManagementFacade();
		}
		return knowledgeFacade;
	}

	private static Map<String, Object> getPermissionStorage() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap();
	}
}
