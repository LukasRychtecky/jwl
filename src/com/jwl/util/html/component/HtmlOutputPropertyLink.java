package com.jwl.util.html.component;

import java.util.Map;

import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;

import com.jwl.util.html.url.URLBuilder;

public class HtmlOutputPropertyLink extends HtmlOutputLink {

	public HtmlOutputPropertyLink(HtmlLinkProperties properties){
		super();
		this.buildLinkFromProperties(properties);
	}

	private void buildLinkFromProperties(HtmlLinkProperties properties) {
		String accesskey = properties.getAccesskey();
		if (null != accesskey) {
			this.setAccesskey(accesskey);
		}

		String url = properties.getHref();
		Map<String, String> attributes = properties.getParameters();
		if (null != url) {
			String link = URLBuilder.buildURL(url, attributes);
			this.setValue(link);
		}

		String style = properties.getStyle();
		if (null != style) {
			this.setStyle(style);
		}

		StringBuilder classes = new StringBuilder();
		for (String className : properties.getClasses()) {
			classes.append(className).append(" ");
		}

		if (classes.length() > 0) {
			this.setStyleClass(classes.substring(0, classes.length() - 1));
		}
		
		String title = properties.getTitle();
		if (null != title) {
			this.setTitle(title);
		}

		String value = properties.getValue();
		if (null != value) {
			HtmlOutputText text = new HtmlOutputText();
			text.setValue(value);
			this.getChildren().add(text);
		}
	}
	
}
