package com.jwl.presentation.search.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.renderer.EncodeEdit;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.presentation.search.enumerations.SearchStates;
import com.jwl.presentation.search.renderer.EncodeSearch;

public class SearchController extends JWLController {

	@Override
	public void processDecode(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException {
		if (component instanceof SearchComponent) {
			this.decoder = new SearchDecoder(getMap(context), component, facade);
			this.decoder.processDecode();
		}
	}

	@Override
	public void processRequest(FacesContext context, UIComponent component)
			throws NoPermissionException {
		this.assertValidInput(context, component);
		this.setUserRoles(component);
	}

	@Override
	public JWLEncoder getResponseEncoder(UIComponent component) {
		SearchStateRecognizer recognizer = new SearchStateRecognizer(facade);
		SearchStates componentState = recognizer.getComponentState(component);
		ArticleId id = recognizer.getArticleId();
		switch (componentState) {
		case INIT:
			return new EncodeSearch(this.facade);
		case EDIT:
			return new EncodeEdit(this.facade, id, isSetUserName(component));
		case VIEW:
			return new EncodeView(this.facade, id);
		default:
			return new EncodeSearch(this.facade);
		}
	}
}
