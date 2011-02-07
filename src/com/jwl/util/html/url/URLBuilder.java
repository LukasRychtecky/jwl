package com.jwl.util.html.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

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
				query.append(encodeToUTF8(entry.getKey()));
				query.append("=");
				query.append(encodeToUTF8(entry.getValue()));
				query.append("&");
			}
			query.deleteCharAt(query.length() - 1);
		}
		
		return query.toString();
	}
	
	private static String encodeToUTF8(String stirng){
		try {
			return URLEncoder.encode(stirng, URLParser.CHARSET);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
