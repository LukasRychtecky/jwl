package com.jwl.util.html.component;

import java.util.Collections;
import java.util.List;

import javax.faces.component.html.HtmlPanelGrid;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlHeaderPanelGrid extends HtmlPanelGrid {

	private List<String> headers;
	
	public HtmlHeaderPanelGrid(){
		super();
		setRendererType(RendererTypes.HEADER_PANEL_GRID_RENDERER);
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;		
	}

	public List<String> getHeaders() {
		if (null != headers) {
			return headers;
		} else {}
			return Collections.emptyList();
	}
	
}
