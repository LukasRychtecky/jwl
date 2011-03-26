package com.jwl.util.html.component;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import com.jwl.util.html.renderer.RendererTypes;

public class HtmlDiv extends HtmlOutputText{
	
	private List<UIComponent> children;
	private List<String> styleClasses;
	
	public HtmlDiv(){
		super();
		setRendererType(RendererTypes.DIV_RENDERER);
		styleClasses = new ArrayList<String>();
		children = new ArrayList<UIComponent>();
	}

	@Override
	public List<UIComponent> getChildren() {
		return children;
	}

	public List<String> getStyleClasses() {
		return styleClasses;
	}
	
	@Override
	public void setStyleClass(String styleClass){
		styleClasses.add(styleClass);
	}
	
	
}
