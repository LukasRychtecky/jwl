package com.jwl.presentation.administration.controller;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.IFacade;
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
import com.jwl.presentation.administration.renderer.EncodeTopicView;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.controller.UIComponentHelper;
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
import com.jwl.presentation.global.Global;

/**
 *
 * @author Lukas Rychtecky
 */
public class AdministrationController implements JWLController {

	private AdministrationStateRecognizer recognizer;
	private IFacade facade;
	private FlashMessage message = null;
	
	public AdministrationController() {
		facade = Global.getInstance().getFacade();
	}

	@Override
	public void processDecode(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException {
		UIComponentHelper.assertValidInput(context, component);

		if (component instanceof AdministrationComponent) {
			Map<String, String> parameterMap = context.getExternalContext()
					.getRequestParameterMap();
			
			JWLDecoder decoder = new AdministrationDecoder(parameterMap, component);
			decoder.processDecode();
			message = new FlashMessage("Article was saved.");
		}
	}

	@Override
	public void processRequest(FacesContext context, UIComponent component)
			throws ModelException, NoPermissionException {
		
		UIComponentHelper.assertValidInput(context, component);
		this.checkRole(component);
		UIComponentHelper.setUserNameAndRoles(component);

		this.recognizer = new AdministrationStateRecognizer();
		switch (recognizer.getAction()) {
			case DELETE:
				this.facade.deleteArticle(recognizer.getArticleId());
				this.message = new FlashMessage("Article was deleted.");
				break;
			case LOCK:
				this.facade.lockArticle(recognizer.getArticleId());
				this.message = new FlashMessage("Article was locked.");
				break;
			case UNLOCK:
				this.facade.unlockArticle(recognizer.getArticleId());
				this.message = new FlashMessage("Article was unlocked.");
				break;
			case RESTORE:
				this.facade.restoreArticle(recognizer.getHistoryId());
				this.message = new FlashMessage("Article was restored.");
				break;
			case IMPORT_ACL:
				this.message = new FlashMessage("import");
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
					encoder = new EncodeListing();
					break;
				case VIEW:
					encoder = new EncodeView(id);
					break;
				case CREATE:
					encoder = new EncodeCreate();
					break;
				case EDIT:
					encoder = new EncodeEdit(id);
					break;
				case HISTORY_LIST:
					encoder = new EncodeHistoryListing(id);
					break;
				case HISTORY_VIEW:
					encoder = new EncodeHistoryView(recognizer.getHistoryId());
					break;
				case MERGE_SUGGESTION_LIST:
					encoder = new EncodeMergeSuggestionList();
					break;
				case MERGE_SUGGESTION_VIEW:
					encoder = new EncodeMergeSuggestionView(id);
					break;
				case DEAD_ARTICLE_LIST:
					encoder = new EncodeDeadArticleList();
					break;
				case DEAD_ARTICLE_VIEW:
					encoder = new EncodeDeadArticleView(id);
					break;
				case FORUM_TOPIC_LIST:
					encoder = new EncodeTopicList(id);
					break;	
				case FORUM_TOPIC_CREATE:
//					encoder = new EncodeCreateTopic(id);
					break;
				case FORUM_TOPIC_VIEW:
					encoder = new EncodeTopicView(facade, recognizer.getTopicId(), id, recognizer.isAnswering(), recognizer.getQuotePostId());
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
		
		if (this.message != null) {
			encoder.addFlashMessage(this.message);
			this.message = null;
		}
		return encoder;
	}
}
