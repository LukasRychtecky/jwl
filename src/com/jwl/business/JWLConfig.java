package com.jwl.business;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for loading config file for configuration JWL. Mainly setting for
 * Environmental variables.
 * 
 * @author Jiri Ostatnicky
 */
public class JWLConfig {

	public static final String CONFIG_FILE_NAME = "config.properties";
	private String configFile;
	
	public static final String IMPLICIT_PU = "jsfwiki";
	public static final String FILESYSTEM_PU = "jsf-filesystem";

	
	public JWLConfig(String configFile) {
		this.configFile = configFile;
		loadConfig();
	}

	private void loadConfig() {
		try {
			FileInputStream fstream = new FileInputStream(configFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				processSetting(strLine);
			}
			in.close();
		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	private void processSetting(String pairText) {
		String pt = pairText.trim();
		if (!pt.startsWith("#")) { // comment
			String[] pair;
			if (pt.indexOf("=") != -1) { // equal
				pair = pt.split("=", 2);
			} else {
				pair = pt.split("\\p{Space}", 2); // or whitespace
			}
			if (pair.length > 1) {
				setupVariable(pair[0].trim(), pair[1].trim());
			}
		}
	}

	private void setupVariable(String variable, String value) {
		if (variable.equals(JWLConfigConstant.PERSISTANCE_UNIT.constantName)) {
			persistanceLayer(value);
		} else if (variable.equals(JWLConfigConstant.FILESYSTEM_STORE.constantName)) {
			JWLConfigConstant.FILESYSTEM_STORE.value = value;
		}
	}

	private void persistanceLayer(String value) {
		if (value.equals(JWLConfigConstant.PERSISTANCE_UNIT.getPermittedOptions()[0])) {
			JWLConfigConstant.PERSISTANCE_UNIT.value = JWLConfig.IMPLICIT_PU;
		} else if (value.equals(JWLConfigConstant.PERSISTANCE_UNIT.getPermittedOptions()[1])) {
			JWLConfigConstant.PERSISTANCE_UNIT.value = JWLConfig.FILESYSTEM_PU ;
		}
	}
	
	public String getPersistanceUnit() {
		return JWLConfigConstant.PERSISTANCE_UNIT.value;
	}

	public void setPersistanceUnit(String persistanceUnit) {
		JWLConfigConstant.PERSISTANCE_UNIT.value = persistanceUnit;
	}

	public String getAclFileName() {
		return JWLConfigConstant.ACL_FILE.value;
	}

}

enum JWLConfigConstant {

	PERSISTANCE_UNIT			("persistance-layer", JWLConfig.IMPLICIT_PU, "db", "xml"),
	FILESYSTEM_STORE			("filesystem-store", ""),
	ACL_FILE					("acl-file", "acl.csv");

	public final String constantName;
	public String value;
	private String[] permittedOptions;

	JWLConfigConstant(String constantName, String value, String... permittedOptions) {
		this.constantName = constantName;
		this.value = value;
		this.permittedOptions = permittedOptions;
	}
	
	public String[] getPermittedOptions() {
		return permittedOptions;
	}

}
