package com.jwl.presentation.html;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public interface AppForm {

	public static final String PREFIX = "jwl-";
	public static final String FORM_NAME = PREFIX + "form-id";

	public UIComponent addText(String name, String label, String value);
	public UIComponent addFile(String name, String label);
	public UIComponent addPassword(String name, String label);
	public UIComponent addHidden(String name, String label, String value);
	public UIComponent addTextArea(String name, String label, String value);
	public UIComponent addSubmit(String name, String caption, String label);
	public UIComponent addCheckbox(String name, String label);

	public HtmlInputExtended get(String name);
	public Map<String, HtmlInputExtended> getInputs();
	public String getAction();
	public void setAction(String action);
	public String getEnctype();
	public void setEnctype(String enctype);
	public String getMethod();
	public void setMethod(String method);

	public void encodeBegin(FacesContext context) throws IOException;
	public void encodeEnd(FacesContext context) throws IOException;
	public void encodeChildren(FacesContext context) throws IOException;
	public void encodeAll(FacesContext context) throws IOException;
}
