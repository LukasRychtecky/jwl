package com.jwl.presentation.knowledgeajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.knowledge.scheduler.ScheduledJobManager;

public class SchedulerStartUpTestServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ScheduledJobManager sjm = new ScheduledJobManager();
		sjm.schedule();
	}
		
}
