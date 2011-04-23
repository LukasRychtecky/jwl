package com.jwl.presentation.global;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.enumerations.JWLURLParams;

public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -2841238126755369558L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			File file =  Global.getInstance().getFacadeOutsideJSF()
					.getFile(request.getParameter(JWLURLParams.FILE_NAME));
			FileDownloader downloader = new FileDownloader(response);
			downloader.writeResponse(file);
		} catch (ModelException ex) {
			ExceptionLogger.severe(getClass(), ex);
		}
	}

	

}
