package com.jwl.presentation.global;

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


/**
 * Attaches javascript and css to pages containing wiki
 */
public class AttachCSSAndJSFilter implements Filter {

	private int headEndPosition;
	private int bodyBeginPosition;
	private static final String JWL_DIRECTORY = "jwl/";

	@Override
	public void destroy() {
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

		headEndPosition = wrapper.toString().indexOf("</head>");
		if (headEndPosition < 0) {
			return;
		}
		bodyBeginPosition = wrapper.toString().indexOf("<body>") + 6;

		StringBuilder builder = new StringBuilder();
		builder.append(wrapper.toString());
		this.addWikiCSS(builder);
		this.addEditToolbarJavascript(builder);
		response.setContentLength(builder.toString().length());
		out.write(builder.toString());
		out.close();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
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
		styles.add(JWL_DIRECTORY + "jquery-ui.css");

		for (String href : styles) {
			String link = "<link rel=\"stylesheet\" type=\"text/css\" "
					+ "href=\"" + href + "\"/>";
			builder.insert(headEndPosition, link);
			headEndPosition += link.length();
			bodyBeginPosition += link.length();
		}

		List<String> scripts = new ArrayList<String>();
		scripts.add(JWL_DIRECTORY + "jquery.js");
		scripts.add(JWL_DIRECTORY + "jquery-ui.min.js");
		scripts.add(JWL_DIRECTORY + "jquery.livequery.js");
		scripts.add(JWL_DIRECTORY + "jwlengine.js");
		scripts.add(JWL_DIRECTORY + "markitup/jquery.markitup.js");
		scripts.add(JWL_DIRECTORY + "markitup/sets/markdown/set.js");

		for (String scriptName : scripts) {
			String script = "<script type=\"text/javascript\" src=\""
					+ scriptName + "\"></script>";
			builder.insert(headEndPosition, script);
			headEndPosition += script.length();
			bodyBeginPosition += script.length();
		}
		
	}

	public void addWikiCSS(StringBuilder builder) {
		String link = "<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ JWL_DIRECTORY + "jwlstyle.css\"/>";
		builder.insert(headEndPosition, link);
		headEndPosition += link.length();
		bodyBeginPosition += link.length();
	}
}
