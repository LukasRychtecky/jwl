package com.jwl.business.exceptions;

/**
 *
 * @author Lukas Rychtecky
 */
public class BreakBusinessRuleException extends ModelException {

	private static final long serialVersionUID = -2908053697197047794L;

	public BreakBusinessRuleException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public BreakBusinessRuleException(String string) {
		super(string);
	}

	public BreakBusinessRuleException(Throwable thrwbl) {
		super(thrwbl);
	}

}
