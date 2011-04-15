package com.jwl.presentation.core;

import com.jwl.presentation.component.enumerations.JWLURLParameters;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author Lukas Rychtecky
 */
public class AjaxListener implements PhaseListener {

	private static final long serialVersionUID = -4978877094917671154L;

	@Override
	public void afterPhase(PhaseEvent pe) {
		try {
			this.handleAjaxRequest(pe);
		} catch (IOException ex) {
			Logger.getLogger(AjaxListener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void beforePhase(PhaseEvent pe) {
	}

	private void handleAjaxRequest(PhaseEvent event) throws IOException {
		
		FacesContext context = event.getFacesContext();
		if (context == null) {
			return;
		}
		
		try {
			Map<String, String> requestParams = context.getExternalContext().getRequestParameterMap();
			String method = requestParams.get(JWLURLParameters.METHOD);
			if (method == null || !method.equals("ajax")) {
				return;
			}

			if (!requestParams.containsKey(JWLURLParameters.PRESENTER)) {
				throw new IllegalArgumentException("No " + JWLURLParameters.PRESENTER + " found.");
			}

			StringBuilder packageName = new StringBuilder(this.getClass().getPackage().getName());
			String className = 
					packageName.substring(0, packageName.lastIndexOf(".")) +
					".presenters." +
					requestParams.get(JWLURLParameters.PRESENTER).toLowerCase() +
					".Component";

			Class c = Class.forName(className);
			AbstractComponent component = (AbstractComponent) c.newInstance();
			component.encodeAll(context);

		} catch (Exception ex) {
			this.handleException(context, ex);
		}

	}

	private void handleException(FacesContext context, Exception ex) throws IOException {
		AbstractPresenter presenter = new AbstractPresenter(context) {};
		if (ex instanceof ClassNotFoundException) {
			presenter.render404();
		} else {
			presenter.render500();
		}
		Logger.getLogger(AjaxListener.class.getName()).log(Level.SEVERE, null, ex);
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
