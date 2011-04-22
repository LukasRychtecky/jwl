package com.jwl.presentation.global;

import com.jwl.business.exceptions.ModelException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -2841238126755369558L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			File file = Global.getInstance().getFacadeOutsideJSF().getFile(request.getParameter("jwlfilename"));
			FileDownloader downloader = new FileDownloader(response);
			downloader.writeResponse(file);
		} catch (ModelException ex) {
			Logger.getLogger(FileDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	

}
