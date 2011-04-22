package com.jwl.presentation.knowledgeajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.Environment;

public class KeyWordServlet extends HttpServlet {

	private static final long serialVersionUID = 3316523429092937634L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Environment.getKnowledgeFacade().extractKeyWords();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Environment.getKnowledgeFacade().extractKeyWords();
	}
	
}
