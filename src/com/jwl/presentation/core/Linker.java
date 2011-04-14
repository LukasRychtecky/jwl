package com.jwl.presentation.core;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.url.URLParser;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class Linker {

	public static final String PREFIX = "jwl";
	private WikiURLParser parser;
	private String presenter;
	private Map<String, String> foreingParams = null;

	public Linker(FacesContext context, String presenter, String uri) {
		this(context, presenter);
		if (uri != null) {
			this.parser.setURI(uri);
		}
	}

	public Linker(FacesContext context, String presenter) {
		this.parser = new WikiURLParser(context);
		this.presenter = presenter;
	}

	public String buildForm(String action) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(JWLURLParameters.DO, action);
		return this.buildLink(params);
	}

	public String build(String action, Map<String, String> params) {
		params.put(JWLURLParameters.PRESENTER, this.presenter);
		if (!action.equals("default")) {
			params.put(JWLURLParameters.ACTION, action);
		}
		return this.buildLink(params);
	}

	public String build(String action) {
		return this.build(action, new HashMap<String, String>());
	}

	private String buildLink(Map<String, String> params) {
		StringBuilder link = new StringBuilder(this.parser.getCurrentURI());
		params.putAll(this.getForeingParams());

		link.append("?");
		for (Entry<String, String> entry : params.entrySet()) {
			link.append(this.encodeToUTF8(entry.getKey()));
			link.append("=");
			link.append(this.encodeToUTF8(entry.getValue()));
			link.append("&");
		}
		link.deleteCharAt(link.length() - 1);
		return link.toString();
	}

	private String encodeToUTF8(String string) {
		try {
			return URLEncoder.encode(string, URLParser.CHARSET);
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, null, e);
			return "";
		}
	}

	private Map<String, String> getForeingParams() {
		if (this.foreingParams == null) {
			this.foreingParams = new HashMap<String, String>();

			for (Entry<String, String> param  : this.parser.getURLParameters().entrySet()) {
				if (!param.getKey().startsWith(PREFIX)) {
					this.foreingParams.put(param.getKey(), param.getValue());
				}
			}
		}
		return foreingParams;
	}
}
