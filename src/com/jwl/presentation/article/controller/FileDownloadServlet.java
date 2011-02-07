package com.jwl.presentation.article.controller;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.IFacade;
import com.jwl.presentation.global.Global;

public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -2841238126755369558L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		IFacade facade =Global.getInstance().getFacadeOtsideJSF();
		facade.makeDownloadFileResponse(req,resp);
	}

	

}
