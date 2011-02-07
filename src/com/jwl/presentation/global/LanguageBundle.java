package com.jwl.presentation.global;

/**
 * 
 * @author Petr Janouch
 * @review Lukas Rychtecky
 *
 */
public class LanguageBundle {

	private static LanguageBundle instance = null;
	public String currentLanguage;

	private LanguageBundle() {
		this.currentLanguage = "english";
	}

	public static LanguageBundle getInstance() {
		if (LanguageBundle.instance == null) {
			LanguageBundle.instance = new LanguageBundle();
		}
		return LanguageBundle.instance;
	}

	public void setLanguage(String language) {
		
	}

	public String getString(String key) {
		return "";
	}
}
