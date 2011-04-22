package com.jwl.presentation.knowledgeajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.Environment;

public class MergeSuggestionTestServlet extends HttpServlet {

	private static final long serialVersionUID = -9135703118061896534L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Environment.getKnowledgeFacade().pregenerateMergeSuggestion();
	}
	
}
