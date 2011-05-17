package com.jwl.presentation.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * 
 * @author Lukas Rychtecky
 */
abstract public class HtmlElement extends UIOutput {
	protected List<String> styleClasses;
	protected String text;

	public HtmlElement() {
		this.styleClasses = new ArrayList<String>();
		this.text = "";
	}

	abstract public String getElement();

	@Override
	public void encodeBegin(FacesContext context) throws IOException{
		ResponseWriter writer = this.getWriter(context);
		writer.startElement(this.getElement(), this);
		if(this.getId() != null) {
			writer.writeAttribute("id", this.getId(), null);
		}
		if(!this.styleClasses.isEmpty()){
			StringBuilder styleClass = new StringBuilder();
			for (String className : this.styleClasses){
				styleClass.append(className).append(" ");
			}
			writer.writeAttribute("class",
					styleClass.substring(0, styleClass.length() - 1), null);
		}
		super.encodeBegin(context);
	}

	@Override
	public void encodeChildren(FacesContext context) throws IOException{
		HtmlOutputText output = new HtmlOutputText();
		output.setValue(this.text);
		super.getChildren().add(output);
		for (UIComponent child : super.getChildren()){
			child.encodeAll(context);
		}
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException{
		this.getWriter(context).endElement(this.getElement());
	}

	protected ResponseWriter getWriter(FacesContext context) throws IOException{
		return context.getResponseWriter();
	}

	public void addChildren(UIComponent component){
		this.getChildren().add(component);
	}

	public void addChildren(List<UIComponent> component){
		this.getChildren().addAll(component);
	}

	public List<String> getStyleClasses(){
		return this.styleClasses;
	}

	public void addStyleClass(String... styleClasses){
		for (String styleClass : styleClasses){
			if(styleClass != null){
				this.styleClasses.add(styleClass);
			}
		}
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text = text;
	}

}
