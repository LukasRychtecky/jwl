package com.jwl.presentation.search.controller;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.controller.UIComponentHelper;
import com.jwl.presentation.component.renderer.EncodeEdit;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.presentation.global.Global;
import com.jwl.presentation.search.enumerations.SearchStates;
import com.jwl.presentation.search.renderer.EncodeSearch;

public class SearchController implements JWLController {

	private IFacade facade;
	
	public SearchController() {
		facade = Global.getInstance().getFacade();
	}
	
	@Override
	public void processDecode(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException {
		if (component instanceof SearchComponent) {
			Map<String, String> parameterMap = context.getExternalContext()
					.getRequestParameterMap();
			
			JWLDecoder decoder = new SearchDecoder(parameterMap, component);
			decoder.processDecode();
		}
	}

	@Override
	public void processRequest(FacesContext context, UIComponent component)
			throws NoPermissionException {
		UIComponentHelper.assertValidInput(context, component);
		UIComponentHelper.setUserNameAndRoles(component);
	}

	@Override
	public JWLEncoder getResponseEncoder(UIComponent component) {
		SearchStateRecognizer recognizer = new SearchStateRecognizer(facade);
		SearchStates componentState = recognizer.getComponentState(component);
		ArticleId id = recognizer.getArticleId();
		switch (componentState) {
		case INIT:
			return new EncodeSearch();
		case EDIT:
			return new EncodeEdit(id);
		case VIEW:
			return new EncodeView(id);
		default:
			return new EncodeSearch();
		}
	}
}
