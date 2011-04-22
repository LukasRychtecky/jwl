package com.jwl.presentation.global;

import java.util.HashMap;
import java.util.Map;

import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.url.URLBuilder;
/**
 * 
 * @author Petr Janouch
 *
 */
public class FileURLBuilder {
	private static final String FILE_DOWNLOAD_URL = "WikiFile";
	
	public static String getFileURL(String fileName){
		Map<String,String> parametres = new HashMap<String, String>();
		parametres.put(JWLURLParams.FILE_NAME, fileName);
		return URLBuilder.buildURL(FILE_DOWNLOAD_URL, parametres);
	}
}
