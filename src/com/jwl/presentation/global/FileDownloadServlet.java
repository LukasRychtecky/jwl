package com.jwl.presentation.global;

import com.jwl.business.IFacade;
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
			IFacade facade = Global.getInstance().getFacadeOutsideJSF();
			FileDownloader downloader = new FileDownloader(response);
			File file = null;
			if (request.getParameter(JWLURLParams.FILE_NAME) != null) {
				file =  facade.getFile(request.getParameter(JWLURLParams.FILE_NAME));
				downloader.writeResponse(file, FileDownloader.CONTENT_TYPE_OCTET_STREAM);
			} else if (request.getParameter(JWLURLParams.DOWNLOAD) != null && request.getParameter(JWLURLParams.DOWNLOAD).equals("ACL")) {
				file = facade.exportACL();
				downloader.writeResponse(file, FileDownloader.CONTENT_TYPE_CSV);
			}
		} catch (ModelException ex) {
			ExceptionLogger.severe(getClass(), ex);
		}
	}	

}
