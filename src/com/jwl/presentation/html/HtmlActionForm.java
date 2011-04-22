package com.jwl.presentation.html;

import javax.faces.component.html.HtmlForm;

public class HtmlActionForm extends HtmlForm {

	private String action;
	
	public HtmlActionForm() {
		super();
		super.setId("jwl");
		super.setRendererType("com.jwl.html.ActionFormRenderer");
		super.setEnctype("application/x-www-form-urlencoded");
	}

	public void setAction(String action){
		this.action = action;
	}
	
	public String getAction(){
		return this.action;
	}
	
}
