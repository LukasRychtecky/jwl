package com.jwl.presentation.global;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.presentation.markdown.MarkupToMarkdown;

/**
 * A servlet that enables preview in MarkDownEditor
 * 
 * @author Petr Janouch
 * @review Jiri Ostatnicky
 */
public class WikiPreviewServlet extends HttpServlet {

	private static final long serialVersionUID = -7352404533083573829L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();		
		String wikiText = request.getParameter("data");

		out.println(MarkupToMarkdown.convertForPreview(wikiText));
		out.flush();
		out.close();
	}

	@Override
	public String getServletInfo() {
		return "A servlet that enables preview in MarkDownEditor.";
	}
}
