package com.jwl.util.html.component;

import javax.faces.component.html.HtmlForm;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlActionForm extends HtmlForm {

	private String action;
	
	public HtmlActionForm() {
		super();
		super.setId("jwl");
		super.setRendererType(RendererTypes.ACTION_FORM_RENDERER);
		super.setEnctype("application/x-www-form-urlencoded");
	}

	public void setAction(String action){
		this.action = action;
	}
	
	public String getAction(){
		return this.action;
	}
	
}
