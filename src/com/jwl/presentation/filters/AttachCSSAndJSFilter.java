package com.jwl.presentation.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.jwl.presentation.component.enumerations.JWLElements;
import com.jwl.presentation.component.enumerations.JWLStyleClass;
import com.jwl.presentation.global.Global;

/**
 * Attaches javascript and css to pages containing wiki
 * 
 * @author Petr Janouch
 * @review Lukas Rychtecky
 */
public class AttachCSSAndJSFilter implements Filter {

	private int headEndPosition;
	private int bodyBeginPosition;
	private static final String JWL_DIRECTORY = "jwl/";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		GenericResponseWrapper wrapper = new GenericResponseWrapper(
				(HttpServletResponse) response);

		chain.doFilter(request, wrapper);
		wrapper.flush();
		PrintWriter out = response.getWriter();
		// saves user's instance of facade in the session
		Global.getInstance().saveFacade();
		// if (wrapper.getContentType() != null
		// && wrapper.getContentType().contains("text/html")
		// && wrapper.toString().indexOf("</head>") != -1
		// && wrapper.toString().indexOf(
		// "id=\"" + JWLElements.JWL_DIV.id + "\"") != -1) {

		headEndPosition = wrapper.toString().indexOf("</head>");
		bodyBeginPosition = wrapper.toString().indexOf("<body>") + 6;

		StringBuilder builder = new StringBuilder();
		builder.append(wrapper.toString());
		this.addWikiCSS(builder);
		this.addEditToolbarJavascript(builder);
		response.setContentLength(builder.toString().length());
		out.write(builder.toString());
		// } else {
		// out.write(wrapper.toString());
		// }
		out.close();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// fc = arg0;
	}

	/**
	 * Attaches javascript to pages containing wiki
	 * 
	 * @author Petr Janouch
	 * @param headEndPosition
	 * @param bodyBeginPosition
	 * @param builder
	 */
	public void addEditToolbarJavascript(StringBuilder builder) {
		List<String> styles = new ArrayList<String>();
		styles.add(JWL_DIRECTORY + "markitup/sets/markdown/style.css");
		styles.add(JWL_DIRECTORY + "markitup/skins/simple/style.css");
		styles.add(JWL_DIRECTORY + "jwlstars.css");

		for (String href : styles) {
			String link = "<link rel=\"stylesheet\" type=\"text/css\" "
					+ "href=\"" + href + "\"/>";
			builder.insert(headEndPosition, link);
			headEndPosition += link.length();
			bodyBeginPosition += link.length();
		}

		List<String> scripts = new ArrayList<String>();
		scripts.add(JWL_DIRECTORY + "jquery.js");
		scripts.add(JWL_DIRECTORY + "jquery.livequery.js");
		scripts.add(JWL_DIRECTORY + "jwlengine.js");
		scripts.add(JWL_DIRECTORY + "markitup/jquery.markitup.js");
		scripts.add(JWL_DIRECTORY + "markitup/sets/markdown/set.js");
		scripts.add(JWL_DIRECTORY + "knowledgemanagement.js");
		scripts.add(JWL_DIRECTORY + "jwlstarscript.js");

		for (String scriptName : scripts) {
			String script = "<script type=\"text/javascript\" src=\""
					+ scriptName + "\"></script>";
			builder.insert(headEndPosition, script);
			headEndPosition += script.length();
			bodyBeginPosition += script.length();
		}

		String script = "<script type=\"text/javascript\">"
			+ "$(document).ready(function()	{"
			+ "$('#"
			+ JWLElements.EDIT_FORM.id
			+ JWLStyleClass.HTML_ID_SEPARATOR
			+ JWLElements.EDIT_TEXT.id
			+ "').markItUp(mySettings);"
			+ "});"
			+ "</script>";
		builder.insert(bodyBeginPosition, script);
		
		script = "<script type=\"text/javascript\">"
			+ "$(document).ready(function()	{"
			+ "$('#"
			+ JWLElements.FORUM_TOPIC_TEXT.id
			+ "').markItUp(mySettings);"
			+ "});"
			+ "</script>";
		builder.insert(bodyBeginPosition, script);

		script = "<script type=\"text/javascript\">"
				+ "$(\"div.jwl-flash-message:not(.jwl-no-hide)\").livequery(function () {"
				+ "var el = $(this);" + "setTimeout(function () {"
				+ "el.animate({\"opacity\": 0}, 1500);" + "el.slideUp();"
				+ "}, 7000);" + "});" + "</script>";
		builder.insert(bodyBeginPosition, script);
	}

	public void addWikiCSS(StringBuilder builder) {
		String link = "<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ JWL_DIRECTORY + "jwlstyle.css\"/>";
		builder.insert(headEndPosition, link);
		headEndPosition += link.length();
		bodyBeginPosition += link.length();
	}
}
