package com.jwl.presentation.html;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;

public class HtmlHeaderPanelGrid extends HtmlPanelGrid {

	private List<UIComponent> headers;
	
	public HtmlHeaderPanelGrid(){
		super();
		setRendererType("com.jwl.html.HeaderPanelGridRenderer");
	}

	public void setHeaders(List<UIComponent> headers) {
		this.headers = headers;
	}
	
	public List<UIComponent> getHeaders() {
		if (null != headers) {
			return headers;
		} else {}
			return Collections.emptyList();
	}
	
	
}
