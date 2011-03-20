package com.jwl.business;

import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.business.knowledge.KnowledgeManagementFacade;
import com.jwl.business.knowledge.util.ISettingsSource;
import com.jwl.business.knowledge.util.SettingsSource;
import com.jwl.business.permissions.IIdentity;
import com.jwl.business.permissions.UserIdentity;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.JPADAOFactory;

/**
 * 
 * @author Lukas Rychtecky
 */
public class Environment {

	public static final String IMPLICIT_PU = "jsfwiki";
	private static String PERSISTENCE_UNIT = IMPLICIT_PU;
	private static IDAOFactory factory = null;
	private static IIdentity identity = null;
private static ISettingsSource knowledgeSettings = null;
	private static IKnowledgeManagementFacade knowledgeFacade = null;

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
			Environment.factory = new JPADAOFactory();
		}
		return Environment.factory;
	}

	public static IIdentity getIdentity() {
		if (Environment.identity == null) {
			Environment.identity = new UserIdentity(Environment.getDAOFactory());
		}
		return Environment.identity;
	}

	public static ISettingsSource getKnowledgeSettings() {
		if (knowledgeSettings == null) {
			knowledgeSettings = new SettingsSource();
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
