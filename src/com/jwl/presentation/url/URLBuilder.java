package com.jwl.presentation.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.jwl.presentation.global.ExceptionLogger;

public class URLBuilder {

	public static String buildURL(String href, Map<String, String> parameters) {
		if (null == href) {
			return "";
		}
		
		StringBuilder query = new StringBuilder();
		query.append(href);		
		
		if(null != parameters && !parameters.isEmpty()){
			query.append("?");
			for (Entry<String, String> entry : parameters.entrySet()) {
				if (entry.getValue() == null) {
					ExceptionLogger.warn(URLBuilder.class, new IllegalArgumentException(
							"URL parameter [" + entry.getKey() + "] could not " +
							"be created. Value is null"));
					continue;
				}
				query.append(encodeToUTF8(entry.getKey()));
				query.append("=");
				query.append(encodeToUTF8(entry.getValue()));
				query.append("&");
			}
			query.deleteCharAt(query.length() - 1);
		}
		
		return query.toString();
	}
	
	private static String encodeToUTF8(String string){
		try {
			return URLEncoder.encode(string, URLParser.CHARSET);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
