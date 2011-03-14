package com.jwl.presentation.filters;

import com.jwl.business.IFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
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
import com.jwl.presentation.administration.enumerations.AdministrationActions;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.component.enumerations.JWLURLParameters;
import com.jwl.presentation.global.FileMover;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.global.WikiURLParser;
import com.jwl.util.html.url.URLBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

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

		UserTransaction ut = this.getUserTransaction();
		try {
			this.moveFile(request);
			FileMover mover = null;
			IFacade facade = Global.getInstance().getFacadeOutsideJSF();
			facade.setJWLHome(request.getSession().getServletContext().getRealPath("/jwl/"));

			if (parser.getFileAction().equals(ArticleActions.ATTACH_FILE)) {
				//do attach
//				ut.begin();
//				IFacade facade = Global.getInstance().getFacadeOutsideJSF();
//				facade.uploadFile(request);
//				ut.commit();
			} else if (parser.getFileAction().equals(AdministrationActions.IMPORT_ACL.toString())) {
				mover = new FileMover(request);
				mover.moveToTMP("acl.csv");
				facade.importACL();
			}


		} catch (Throwable e) {
			Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, e);
			
			try {
				ut.rollback();
			} catch (Throwable t) {
				Logger.getLogger(FileUploadFilter.class.getName()).log(Level.SEVERE, null, t);
			}
		}

		String URL = this.getURL(parser);
		PrintWriter pw = response.getWriter();
		pw.println(htmlRedirect.replace("_URL_", URL));
		pw.close();
		response.setContentType("text/html");
	}

	protected void moveFile(HttpServletRequest request) {
	}

	private String getURL(WikiURLParser parser) {
		String currentURL = parser.getCurrentURL();
		currentURL = currentURL.substring(0, currentURL.lastIndexOf("/") + 1);

		String href = currentURL + parser.getRedirectTarget();
		Map<String, String> params = parser.getURLParametersAndArticleTitle();
		params.put(JWLURLParameters.ACTION, ArticleActions.VIEW);

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
