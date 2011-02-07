package com.jwl.presentation.global;

import java.util.HashMap;
import java.util.Map;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.util.html.url.URLBuilder;
/**
 * 
 * @author Petr Janouch
 *
 */
public class FileURLBuilder {
	private static final String FILE_DOWNLOAD_URL = "WikiFile";
	
	public static String getFileURL(String fileName){
		Map<String,String> parametres = new HashMap<String, String>();
		parametres.put(JWLURLParameters.FILE_NAME, fileName);
		return URLBuilder.buildURL(FILE_DOWNLOAD_URL, parametres);
	}
}
