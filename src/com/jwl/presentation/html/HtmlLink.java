package com.jwl.presentation.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlLink extends HtmlOutputLink {

	private Boolean isAjax = Boolean.TRUE;
	private List<String> styleClasses;

	public HtmlLink() {
		super();
		this.styleClasses = new ArrayList<String>();
		this.setText("");
	}

	public Boolean isAjax() {
		return this.isAjax;
	}

	public void setIsAjax(Boolean isAjax) {
		this.isAjax = isAjax;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		StringBuilder styleClass = new StringBuilder();

		if (this.isAjax) {
			this.styleClasses.add("jwl-ajax");
		}

		for (String className : this.styleClasses) {
			styleClass.append(className).append(" ");
		}
		super.setStyleClass(styleClass.substring(0, styleClass.length() - 1));
		super.encodeBegin(context);
	}

	public List<String> getStyleClasses() {
		return styleClasses;
	}

	public final void setText(String text) {
		HtmlOutputText textNode = new HtmlOutputText();
		textNode.setValue(text);
		if (super.getChildren().isEmpty()) {
			super.getChildren().add(textNode);
		} else {
			super.getChildren().set(0, textNode);
		}
	}

}
