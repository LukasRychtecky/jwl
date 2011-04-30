package com.jwl.business;

import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.KnowledgeManagementFacade;
import com.jwl.business.security.IIdentity;
import com.jwl.business.security.UserIdentity;


import com.jwl.business.knowledge.util.ISettingsSource;
import com.jwl.business.knowledge.util.SettingsSource;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.JPADAOFactory;
import com.jwl.integration.filesystem.FSDAOFactory;
import java.io.File;

/**
 * 
 * @author Lukas Rychtecky
 */
public class Environment {

	public static final String IMPLICIT_PU = "jsfwiki";
	public static final String FILESYSTEM_PU = "jsf-filesystem";
	private static String JWL_HOME = "jwl";
	private static String PERSISTENCE_UNIT = IMPLICIT_PU;
//	 private static String PERSISTENCE_UNIT = FILESYSTEM_PU;
	private static IDAOFactory factory = null;
	private static IIdentity identity = null;
	private static ISettingsSource knowledgeSettings = null;
	private static IKnowledgeManagementFacade knowledgeFacade = null;
	private static final String ACL_FILE_NAME = "acl.csv";
	private static final String FILESYSTEM_STORE = "/Users/ostatnickyjiri/Desktop";

	private Environment() {

	}

	public static String getPersistenceUnit() {
		return Environment.PERSISTENCE_UNIT;
	}

	public static void setPersistenceUnit(String persistenceUnit) {
		if (persistenceUnit != null && !persistenceUnit.isEmpty()) {
			Environment.PERSISTENCE_UNIT = persistenceUnit;
		}
	}

	public static IDAOFactory getDAOFactory() {
		if (Environment.factory == null) {
			if (Environment.PERSISTENCE_UNIT == Environment.FILESYSTEM_PU) {
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
		Environment.JWL_HOME = jwlHome;
	}

	public static String getACLFileName() {
		return Environment.JWL_HOME + File.separator + "private" + File.separator + "tmp" + File.separator + Environment.ACL_FILE_NAME;
	}

	public static String getAttachmentStorage() {
		return Environment.JWL_HOME + File.separator + "private" + File.separator + "jwl-files";
	}

	public static IIdentity getIdentity() {
		if (Environment.identity == null) {
			Environment.identity = new UserIdentity(Environment.getDAOFactory());
		}
		return Environment.identity;
	}
	public static ISettingsSource getKnowledgeSettings() {
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
}
