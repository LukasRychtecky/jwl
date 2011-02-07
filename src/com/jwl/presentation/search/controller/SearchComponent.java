package com.jwl.presentation.search.controller;

import javax.faces.context.FacesContext;

import com.jwl.presentation.component.controller.JWLComponent;

public class SearchComponent extends JWLComponent {

	public static final String COMPONENT_TYPE = "com.jwl.component.Search";
	public static final String DEFAULT_RENDERER = "com.jwl.component.SearchRenderer";

	public SearchComponent() {
		super();
		this.setRendererType(DEFAULT_RENDERER);
	}

	@Override
	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
		values[0] = super.saveState(context);
		return values;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
	}
}
