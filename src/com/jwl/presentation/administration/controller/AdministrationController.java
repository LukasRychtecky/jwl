package com.jwl.presentation.administration.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.RoleTypes;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.security.Role;
import com.jwl.presentation.administration.enumerations.AdministrationStateRecognizer;
import com.jwl.presentation.administration.enumerations.AdministrationStates;
import com.jwl.presentation.administration.renderer.EncodeDeadArticleList;
import com.jwl.presentation.administration.renderer.EncodeDeadArticleView;
import com.jwl.presentation.administration.renderer.EncodeListing;
import com.jwl.presentation.administration.renderer.EncodeMergeSuggestionList;
import com.jwl.presentation.administration.renderer.EncodeMergeSuggestionView;
import com.jwl.presentation.administration.renderer.EncodeTopicList;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.renderer.EncodeCreate;
import com.jwl.presentation.component.renderer.EncodeCreateTopic;
import com.jwl.presentation.component.renderer.EncodeEdit;
import com.jwl.presentation.component.renderer.EncodeError;
import com.jwl.presentation.component.renderer.EncodeHistoryListing;
import com.jwl.presentation.component.renderer.EncodeHistoryView;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.presentation.component.renderer.FlashMessage;
import com.jwl.presentation.component.renderer.FlashMessageType;
import com.jwl.presentation.component.renderer.JWLEncoder;

/**
 *
 * @author Lukas Rychtecky
 */
public class AdministrationController extends JWLController {

	private AdministrationStateRecognizer recognizer;

	public AdministrationController() {
		super();
	}

	@Override
	public void processDecode(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException {
		this.assertValidInput(context, component);

		if (component instanceof AdministrationComponent) {
			this.decoder = new AdministrationDecoder(getMap(context), component, facade);
			this.decoder.processDecode();
			super.message = new FlashMessage("Article was saved.");
		}
	}

	@Override
	public void processRequest(FacesContext context, UIComponent component)
			throws ModelException, NoPermissionException {
		
		this.assertValidInput(context, component);
		this.setUserRoles(component);
		this.checkRole(component);
		this.setUserName(component);

		this.recognizer = new AdministrationStateRecognizer(super.facade);
		switch (recognizer.getAction()) {
			case DELETE:
				this.facade.deleteArticle(recognizer.getArticleId());
				super.message = new FlashMessage("Article was deleted.");
				break;
			case LOCK:
				this.facade.lockArticle(recognizer.getArticleId());
				super.message = new FlashMessage("Article was locked.");
				break;
			case UNLOCK:
				this.facade.unlockArticle(recognizer.getArticleId());
				super.message = new FlashMessage("Article was unlocked.");
				break;
			case RESTORE:
				this.facade.restoreArticle(recognizer.getHistoryId());
				super.message = new FlashMessage("Article was restored.");
				break;
			case IMPORT_ACL:
				super.message = new FlashMessage("import");
				break;
			default:
				break;
		}
	}

	private void checkRole(UIComponent component) throws NoPermissionException {
		AdministrationComponent adminComponent = (AdministrationComponent) component;

		for (Role role : adminComponent.getRoles()) {
			if (role.getCode().equalsIgnoreCase(RoleTypes.ADMINISTRATOR.toString())) {
				return;
			}
		}
		throw new NoPermissionException("No permission for roles");
	}

	@Override
	public JWLEncoder getResponseEncoder(UIComponent component) {
		JWLEncoder encoder = null;
		try {
			AdministrationStates componentState = recognizer.getComponentState(component);
			ArticleId id = recognizer.getArticleId();

			switch (componentState) {
				case LIST:
					encoder = new EncodeListing(super.facade);
					break;
				case VIEW:
					encoder = new EncodeView(super.facade, id);
					break;
				case CREATE:
					encoder = new EncodeCreate(super.facade, isSetUserName(component));
					break;
				case EDIT:
					encoder = new EncodeEdit(super.facade, id, isSetUserName(component));
					break;
				case HISTORY_LIST:
					encoder = new EncodeHistoryListing(super.facade, id);
					break;
				case HISTORY_VIEW:
					encoder = new EncodeHistoryView(super.facade, recognizer.getHistoryId());
					break;
				case MERGE_SUGGESTION_LIST:
					encoder = new EncodeMergeSuggestionList(facade);
					break;
				case MERGE_SUGGESTION_VIEW:
					encoder = new EncodeMergeSuggestionView(facade, id);
					break;
				case DEAD_ARTICLE_LIST:
					encoder = new EncodeDeadArticleList(facade);
					break;
				case DEAD_ARTICLE_VIEW:
					encoder = new EncodeDeadArticleView(facade, id);
					break;
				case FORUM_TOPIC_LIST:
					encoder = new EncodeTopicList(facade, id);
					break;	
				case FORUM_TOPIC_CREATE:
					encoder = new EncodeCreateTopic(facade, id);
					break;
				default:
					encoder = new EncodeError();
					encoder.addImplicitErrorFlashMessage();
					break;
			}
		} catch (NumberFormatException e) {
			Logger.getLogger(AdministrationController.class.getName()).log(Level.SEVERE, null, e);
			if (encoder == null) {
				encoder = new EncodeError();
			}
			encoder.addFlashMessage("Object not found.", FlashMessageType.WARNING, Boolean.FALSE);
		} catch (ModelException e) {
			Logger.getLogger(AdministrationController.class.getName()).log(Level.SEVERE, null, e);
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
}
