package com.jwl.presentation.knowledgeajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.knowledge.IKnowledgeManagementFacade;
import com.jwl.presentation.enumerations.JWLStates;
import com.jwl.presentation.enumerations.JWLURLParams;
import com.jwl.presentation.url.URLBuilder;

public class SimilarArticleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		String title = req.getParameter("title");
		String tags = req.getParameter("tags");
		String text = req.getParameter("text");
		IKnowledgeManagementFacade knowledgeFacade = Environment
				.getKnowledgeFacade();
		List<ArticleTO> suggestedArticles = knowledgeFacade
				.suggestSimilarArticlesEdit(tags, title, text);
		PrintWriter out = resp.getWriter();
		out.println("<ul>");
		for (ArticleTO sa : suggestedArticles) {
			out.println("<li>");
			out.println(this.getArticleLink(sa.getTitle()));
			out.println("</li>");
		}
		out.println("</ul>");
		out.flush();
		out.close();
	}

	private String getArticleLink(String title) {
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put(JWLURLParams.ARTICLE_TITLE, title);
		urlParams.put(JWLURLParams.STATE, JWLStates.ARTICLE_VIEW.id);
		String url = URLBuilder.buildURL(
				"http://localhost:8080/SeamWiki/wiki.seam", urlParams);
		StringBuilder link = new StringBuilder();
		link.append("<a href =\"");
		link.append(url);
		link.append("\" >");
		link.append(title);
		link.append("</a>");
		return link.toString();
	}
}
