package com.jwl.presentation.article.controller;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.article.enumerations.ArticleActions;
import com.jwl.presentation.article.enumerations.ArticleStates;
import com.jwl.presentation.article.renderer.EncodeAttach;
import com.jwl.presentation.article.renderer.EncodeListing;
import com.jwl.presentation.article.renderer.EncodeNotExist;
import com.jwl.presentation.component.controller.JWLController;
import com.jwl.presentation.component.controller.JWLDecoder;
import com.jwl.presentation.component.controller.UIComponentHelper;
import com.jwl.presentation.component.renderer.EncodeCreate;
import com.jwl.presentation.component.renderer.EncodeCreateTopic;
import com.jwl.presentation.component.renderer.EncodeEdit;
import com.jwl.presentation.component.renderer.EncodeError;
import com.jwl.presentation.component.renderer.EncodeHistoryListing;
import com.jwl.presentation.component.renderer.EncodeHistoryView;
import com.jwl.presentation.component.renderer.EncodeTopicList;
import com.jwl.presentation.component.renderer.EncodeTopicView;
import com.jwl.presentation.component.renderer.EncodeView;
import com.jwl.presentation.component.renderer.FlashMessage;
import com.jwl.presentation.component.renderer.FlashMessageType;
import com.jwl.presentation.component.renderer.JWLEncoder;
import com.jwl.presentation.global.Global;

public class ArticleController implements JWLController {

	private ArticleStateRecognizer recognizer;
	private IFacade facade;
	private FlashMessage message = null;
	
	public ArticleController() {
		facade = Global.getInstance().getFacade();
	}

	@Override
	public void processDecode(FacesContext context, UIComponent component) 
		throws ModelException, NoPermissionException {
		
		UIComponentHelper.assertValidInput(context, component);

		if (component instanceof ArticleComponent) {
			Map<String, String> parameterMap = context.getExternalContext()
					.getRequestParameterMap();
			JWLDecoder decoder = new ArticleDecoder(parameterMap, component);
			decoder.processDecode();
			this.message = new FlashMessage("Article was saved.");
		}
	}

	@Override
	public JWLEncoder getResponseEncoder(UIComponent component) {
		JWLEncoder encoder = null;
		try {
			ArticleStateRecognizer recognizer = new ArticleStateRecognizer();
			ArticleStates componentState = recognizer.getComponentState(component);
			ArticleId id = recognizer.getArticleId();
			
			switch (componentState) {
				case LIST:
					encoder = new EncodeListing();
					break;
				case VIEW:
					encoder = new EncodeView(id);
					break;
				case EDIT:
					encoder = new EncodeEdit(id);
					break;
				case CREATE:
					encoder = new EncodeCreate();
					break;
				case ATTACH_FILE:
					encoder = new EncodeAttach();
					break;
				case NOT_EXIST:
					encoder = new EncodeNotExist();
					break;
				case HISTORY_LIST:
					encoder = new EncodeHistoryListing(id);
					break;
				case HISTORY_VIEW:
					encoder = new EncodeHistoryView(recognizer.getHistoryId());
					break;
				case FORUM_TOPIC_LIST:
					encoder = new  EncodeTopicList(id);
					break;
				case FORUM_TOPIC_CREATE:
//					encoder = new  EncodeCreateTopic(id);
					break;
				case FORUM_TOPIC_VIEW:
					encoder = new  EncodeTopicView(facade, recognizer.getTopicId(), id, recognizer.isAnswering(), recognizer.getQuotePostId());
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
		if (this.message != null) {
			encoder.addFlashMessage(this.message);
			this.message = null;
		}
		return encoder;
	}

	@Override
	public void processRequest(FacesContext context, UIComponent component)
			throws NoPermissionException, ModelException {

		UIComponentHelper.assertValidInput(context, component);
		UIComponentHelper.setUserNameAndRoles(component);
		
		this.recognizer = new ArticleStateRecognizer();

		if (this.recognizer.getAction().equals(ArticleActions.RESTORE)) {
			this.facade.restoreArticle(recognizer.getHistoryId());
			this.message = new FlashMessage("Article was restored.");
		}
	}
}
