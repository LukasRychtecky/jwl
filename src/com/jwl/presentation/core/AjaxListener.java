package com.jwl.presentation.core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Lukas Rychtecky
 */
public class AjaxListener implements PhaseListener {

	private static final long serialVersionUID = -4978877094917671154L;
	private static final String AJAX_VIEW_ID = "ajax-listener";

	@Override
	public void afterPhase(PhaseEvent pe) {
		this.handleAjaxRequest(pe);
	}

	@Override
	public void beforePhase(PhaseEvent pe) {

	}

	private void handleAjaxRequest(PhaseEvent event) {
		FacesContext context = event.getFacesContext();

		if (context == null) {
			return;
		}
		
		for (String key : context.getExternalContext().getRequestParameterMap().keySet()) {
			System.out.println("KEY: " + key);
		}

		if (context.getExternalContext().getRequestParameterMap().get("neco") == null) {
			return;
		}
		HtmlOutputText message = new HtmlOutputText();
		message.setValue("AJAX!!!");
		String r = message.getRendererType();
		try {
			ResponseStream res = context.getRenderKit().createResponseStream(context.getExternalContext().getResponseOutputStream());
			
		} catch (IOException ex) {
			Logger.getLogger(AjaxListener.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			response.getWriter().write("<b>AJAX!!!</b>");
			context.renderResponse();
			context.responseComplete();
		} catch (IOException ex) {
			Logger.getLogger(AjaxListener.class.getName()).log(Level.SEVERE, null, ex);
		}


	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
