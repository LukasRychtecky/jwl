package com.jwl.business;

/**
 *
 * @author Lukas Rychtecky
 */
public class Environment {

	public static final String IMPLICIT_PU = "jsfwiki";
	private static String PERSISTENCE_UNIT = IMPLICIT_PU;

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


}
