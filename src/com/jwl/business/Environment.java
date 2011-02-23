package com.jwl.business;

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

	private Environment() {

	}

	public static String getPersistenceUnit() {
		return Environment.PERSISTENCE_UNIT;
	}

	public static void setPersistenceUnit(String persistenceUnit) {
		if (persistenceUnit != null && persistenceUnit.length() > 0) {
			Environment.PERSISTENCE_UNIT = persistenceUnit;
		}
	}

	public static IDAOFactory getDAOFactory() {
		if (Environment.factory == null) {
			Environment.factory = new JPADAOFactory();
		}
		return Environment.factory;
	}


}
