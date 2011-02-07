package com.jwl.presentation.article.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.article.enumerations.ArticleStates;
import com.jwl.presentation.article.renderer.EncodeAttach;
import com.jwl.presentation.article.renderer.EncodeListing;
import com.jwl.presentation.article.renderer.EncodeNotExist;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.renderer.EncodeCreate;
import com.jwl.presentation.component.renderer.EncodeEdit;
import com.jwl.presentation.component.renderer.EncodeError;
import com.jwl.presentation.component.renderer.EncodeHistoryListing;
import com.jwl.presentation.component.renderer.EncodeHistoryView;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.presentation.component.renderer.FlashMessage;
import com.jwl.presentation.component.renderer.FlashMessageType;
import com.jwl.presentation.component.renderer.JWLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleController extends JWLController {

	private ArticleStateRecognizer recognizer;

	public ArticleController() {
		super();
	}

	@Override
	public void processDecode(FacesContext context, UIComponent component) throws ModelException, NoPermissionException {
		this.assertValidInput(context, component);

		if (component instanceof ArticleComponent) {
			this.decoder = new ArticleDecoder(getMap(context), component, facade);
			this.decoder.processDecode();
			super.message = new FlashMessage("Article was saved.");
		}
	}

	@Override
	public JWLEncoder getResponseEncoder(UIComponent component) {
		JWLEncoder encoder = null;
		try {
			ArticleStateRecognizer recognizer = new ArticleStateRecognizer(facade);
			ArticleStates componentState = recognizer.getComponentState(component);
			ArticleId id = recognizer.getArticleId();
			
			switch (componentState) {
				case LIST:
					encoder = new EncodeListing(this.facade);
					break;
				case VIEW:
					encoder = new EncodeView(this.facade, id);
					break;
				case EDIT:
					encoder = new EncodeEdit(this.facade, id, isSetUserName(component));
					break;
				case CREATE:
					encoder = new EncodeCreate(this.facade, isSetUserName(component));
					break;
				case ATTACH_FILE:
					encoder = new EncodeAttach(this.facade);
					break;
				case NOT_EXIST:
					encoder = new EncodeNotExist(this.facade);
					break;
				case HISTORY_LIST:
					encoder = new EncodeHistoryListing(super.facade, id);
					break;
				case HISTORY_VIEW:
					encoder = new EncodeHistoryView(super.facade, recognizer.getHistoryId());
					break;
				default:
					encoder = new EncodeError();
					encoder.addImplicitErrorFlashMessage();
			}
		} catch (NumberFormatException e) {
			Logger.getLogger(ArticleController.class.getName()).log(Level.SEVERE, null, e);
			if (encoder == null) {
				encoder = new EncodeError();
			}
			encoder.addFlashMessage("Object not found.", FlashMessageType.WARNING, Boolean.FALSE);
		} catch (ModelException e) {
			Logger.getLogger(ArticleController.class.getName()).log(Level.SEVERE, null, e);
			if (encoder == null) {
				encoder = new EncodeError();
			}
			encoder.addImplicitErrorFlashMessage();
		}
		if (super.message != null) {
			encoder.addFlashMessage(this.message);
			super.message = null;
		}
		return encoder;
	}

	@Override
	public void processRequest(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException {

		this.assertValidInput(context, component);
		this.setUserRoles(component);

		this.recognizer = new ArticleStateRecognizer(super.facade);

		if (this.recognizer.getAction().equals(ArticleActions.RESTORE)) {
			this.facade.restoreArticle(recognizer.getHistoryId());
			super.message = new FlashMessage("Article was restored.");
		}
	}
}
