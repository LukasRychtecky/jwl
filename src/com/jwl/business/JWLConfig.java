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

	private String configFile;
	
	private final String PERSISTANCE_LAYER = "persistance-layer";
	private final String OPTION_PERSISTANCE_LAYER_DB = "db";
	private final String OPTION_PERSISTANCE_LAYER_XML = "xml";
	private final String FILESYSTEM_STORE = "filesystem-store";
	private final String KNOWLEDGE_SETTINGS_FILE = "knowledge-settings-file";

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
		if (variable.equals(PERSISTANCE_LAYER)) {
			persistanceLayer(value);
		} else if (variable.equals(FILESYSTEM_STORE)) {
			filesystemStore(value);
		} else if (variable.equals(KNOWLEDGE_SETTINGS_FILE)) {
			knowledgeSettingsFile(value);
		}
	}

	private void persistanceLayer(String value) {
		if (value.equals(OPTION_PERSISTANCE_LAYER_DB)) {
			Environment.setPersistenceUnit(Environment.IMPLICIT_PU);
		} else if (value.equals(OPTION_PERSISTANCE_LAYER_XML)) {
			Environment.setPersistenceUnit(Environment.FILESYSTEM_PU);
		}
	}
	
	private void filesystemStore(String value) {
		Environment.setFilesystemStore(value);
	}
	
	private void knowledgeSettingsFile(String value) {
		Environment.setKnowledgeSettingsFile(value);
	}

}
