package com.jwl.business;

import com.jwl.business.permissions.IIdentity;
import com.jwl.business.permissions.UserIdentity;
import com.jwl.integration.IDAOFactory;
import com.jwl.integration.JPADAOFactory;
import com.jwl.integration.filesystem.FSDAOFactory;

/**
 * 
 * @author Lukas Rychtecky
 */
public class Environment {

	public static final String IMPLICIT_PU = "jsfwiki";
	public static final String FILESYSTEM_PU = "jsf-filesystem";
	private static String PERSISTENCE_UNIT = IMPLICIT_PU;
//	 private static String PERSISTENCE_UNIT = FILESYSTEM_PU;
	private static IDAOFactory factory = null;
	private static IIdentity identity = null;

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

	public static IIdentity getIdentity() {
		if (Environment.identity == null) {
			Environment.identity = new UserIdentity(Environment.getDAOFactory());
		}
		return Environment.identity;
	}
}
