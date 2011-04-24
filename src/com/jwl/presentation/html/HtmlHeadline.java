package com.jwl.presentation.html;

/**
 *
 * @author Lukas Rychtecky
 */
public class HtmlHeadline extends HtmlElement {
	
	protected String element;

	public HtmlHeadline(Integer type) {
		if (type < 1 || 6 < type) {
			type = 1;
		}
		this.element = "h" + type;
	}

	@Override
	public String getElement() {
		return this.element;
	}
	
	
}
