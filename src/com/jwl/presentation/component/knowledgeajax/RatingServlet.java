package com.jwl.presentation.component.knowledgeajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.global.Global;

public class RatingServlet extends HttpServlet {

	private static final long serialVersionUID = -4533476962595784198L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String ratedValue = req.getParameter("ratedValue");
		String articleId = req.getParameter("articleId");
		float ratedValueNum = Float.parseFloat(ratedValue);
		int articleIdNum = Integer.parseInt(articleId);
		ArticleId aic = new ArticleId(articleIdNum);
		try {
			Global.getInstance().getFacadeOtsideJSF()
					.rateArticle(aic, ratedValueNum);
		} catch (ModelException e) {

		}
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		try {
			ArticleTO article = Global.getInstance().getFacadeOtsideJSF()
					.getArticle(aic);
			int sn = (int) article.getRatingAverage();
			int r = (int) (article.getRatingAverage() * 10) % 1;
			if (r >= 5) {
				sn++;
			}
			out.print(sn);
		} catch (ModelException e) {

		}
		out.flush();
		out.close();
	}

}
