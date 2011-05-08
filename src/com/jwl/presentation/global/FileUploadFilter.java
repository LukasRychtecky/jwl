package com.jwl.presentation.global;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import com.jwl.business.IFacade;
import com.jwl.presentation.enumerations.JWLActions;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.url.URLBuilder;
import com.jwl.presentation.url.WikiURLParser;
import org.apache.commons.fileupload.FileUploadException;

public class FileUploadFilter implements Filter {

	private String htmlRedirect =
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\""
			+ " \"http://www.w3.org/TR/html4/frameset.dtd\">"
			+ "<HTML>"
			+ "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=_URL_\">"
			+ "</HTML>";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		WikiURLParser parser = new WikiURLParser(request);

		FileMover mover = null;
		IFacade facade = Global.getInstance().getFacadeOutsideJSF();
		facade.setJWLHome(request.getSession().getServletContext().getRealPath("/jwl/"));
		if (parser.getDoAction().endsWith("fileUpload")) {

			UserTransaction ut = this.getUserTransaction();
			try {
				mover = new FileMover(request);
				String source = mover.moveToTMP();
				ut.begin();
				facade.uploadAttachment(mover.getAttachment(), source);
				ut.commit();

			} catch (Throwable e) {
				Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, e);

				try {
					ut.rollback();
				} catch (Throwable t) {
					Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, t);
				}
			}
		} else if (parser.getDoAction().equals(JWLActions.IMPORT_ACL.id)) {
			try {
				mover = new FileMover(request);
				mover.moveToTMP();
			} catch (FileUploadException ex) {
				Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}



		String URL = this.getURL(parser);
		PrintWriter pw = response.getWriter();
		pw.println(htmlRedirect.replace("_URL_", URL));
		pw.close();
		response.setContentType("text/html");
	}

	private String getURL(WikiURLParser parser) {
		String currentURL = parser.getCurrentURL();
		currentURL = currentURL.substring(0, currentURL.lastIndexOf("/") + 1);

		String href = currentURL + parser.getRedirectTarget();
		Map<String, String> params = parser.getURLParameters();
		params.remove(JWLURLParams.DO);

		return URLBuilder.buildURL(href, params);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private UserTransaction getUserTransaction() {
		UserTransaction ut = null;
		try {
			InitialContext context = new InitialContext();
			ut = (UserTransaction) context.lookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, e);
		}
		return ut;
	}
}
