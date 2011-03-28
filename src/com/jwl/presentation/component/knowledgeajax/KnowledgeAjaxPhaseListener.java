package com.jwl.presentation.component.knowledgeajax;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class KnowledgeAjaxPhaseListener implements PhaseListener {

	private static final long serialVersionUID = -4978877094917671154L;

	@Override
	public void afterPhase(PhaseEvent event) {

	//	String viewId = event.getFacesContext().getViewRoot().getViewId();
	//	if (viewId.indexOf(".jwlajax") != -1) {
	//		System.out.println("KNOWLEDGE AJAX PHASE LISTENER");
	//	}

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
