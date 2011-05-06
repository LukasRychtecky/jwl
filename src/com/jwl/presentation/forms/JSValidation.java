package com.jwl.presentation.forms;

import com.jwl.presentation.core.JSONEncoder;
import com.jwl.presentation.html.HtmlAppForm;
import com.jwl.presentation.html.HtmlInputExtended;
import java.util.Iterator;

/**
 *
 * @author Lukas Rychtecky
 */
public class JSValidation {
	
	private HtmlAppForm form;

	public JSValidation(HtmlAppForm form) {
		this.form = form;
	}
	
	public String generate() {
		StringBuilder script = new StringBuilder("JWL.forms['" + this.form.getId() +"'] = [");
		
		Iterator<String> keys = this.form.getInputs().keySet().iterator();
		while (keys.hasNext()) {
			
			HtmlInputExtended input = this.form.get(keys.next());
			
			if (input.getRule() == null) {
				continue;
			}
			
			script.append("{name: '").append(input.getComponent().getId()).append("',");
			script.append("validate: ").append("function(value) {");
			script.append("if (");
			script.append(this.createValidation(input.getRule()));
			script.append(") {");
			script.append("return null;");
			script.append("} else {");
			script.append("return ").append(this.escape(input.getRule().getMessage())).append(";");
			script.append("}}}");
			if (keys.hasNext()) {
				script.append(",");
			}
		}
		
		script.append("];");
		
		script.append("$('form#").append(this.form.getId()).append("').submit(function() {");
		script.append("return JWL.validateForm(this);");
		script.append("});");
		return script.toString();
	}
	
	protected String createValidation(Rule rule) {
		String output = "";
		switch (rule.getType()) {
			case EQUAL:
				output = this.createEqual(rule);
				break;
			case NOT_EQUAL:
				output = this.createNotEqual(rule);
				break;
			case FILLED:
				output = this.createFilled(rule);
				break;
			case NUMERIC:
				output = this.createNumeric(rule);
				break;
			case LENGTH:
				output = this.createLength(rule);
				break;
			default:
				output = "false";
		}
		return output;
	}
	
	protected String createNumeric(Rule rule) {
		return "/^-?[0-9]+$/.test(value)";
	}
	
	protected String createLength(Rule rule) {
		this.checkArgs(rule);
		Integer length = Integer.parseInt(rule.getArgs().get(0).toString());
		return "$.trim(value).length === " + length;
	}
	
	protected String createEqual(Rule rule) {
		this.checkArgs(rule);
		return "value == " + this.escape(rule.getArgs().get(0).toString());
	}
	
	protected String createNotEqual(Rule rule) {
		this.checkArgs(rule);
		return "value != " + this.escape(rule.getArgs().get(0).toString());
	}
	
	protected String createFilled(Rule rule) {
		return "$.trim(value).length > 0";
	}
	
	protected String escape(String value) {
		return JSONEncoder.quote(value);
	}
	
	private void checkArgs(Rule rule) {		
		if (rule.getArgs().isEmpty()) {
			throw new IllegalArgumentException("Missing argument index 0.");
		}
	}
}
