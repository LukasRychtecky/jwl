package com.jwl.util.html.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HtmlLinkProperties {
	
	private String value;
	private String href;
	private String anchor;
	private String accesskey;
	private String style;
	private String title;
	private boolean disabled;
	private Set<String> classes;
	
	private Map<String, String> parameters;
	
	public HtmlLinkProperties(){
		this.parameters = new HashMap<String, String>();
		this.classes = new HashSet<String>();
	}

	public HtmlLinkProperties(String value, String href, String anchor,
			String accesskey, String style, String title,
			boolean disabled, Map<String, String> attributes) {
		super();
		this.value = value;
		this.href = href;
		this.anchor = anchor;
		this.accesskey = accesskey;
		this.style = style;
		this.title = title;
		this.disabled = disabled;
		this.parameters = attributes;
		this.classes = new HashSet<String>();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getAccesskey() {
		return accesskey;
	}

	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameters(Map<String, String> paramteres) {
		this.parameters.putAll(parameters);
	}
	
	public void addParameter(String parameter, String value) {
		this.parameters.put(parameter, value);
	}
	
	public void addParameter(String parameter, Integer value) {
		this.parameters.put(parameter, String.valueOf(value));
	}

	public boolean addClass(String className) {
		return this.classes.add(className);
	}

	public boolean removeClass(String className) {
		return this.classes.remove(className);
	}

	public Set<String> getClasses() {
		return Collections.unmodifiableSet(this.classes);
	}


	
}
