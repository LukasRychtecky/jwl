package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.naming.NoPermissionException;

import com.jwl.business.exceptions.ArticleExistsException;
import com.jwl.business.exceptions.BreakBusinessRuleException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.controller.JWLController;

/**
 *
 * @author Lukas Rychtecky
 */
public class JWLCoreRenderer implements JWLRenderer {

	protected JWLController controller;
	protected List<FlashMessage> messages;

	public JWLCoreRenderer(JWLController controller) {
		this.controller = controller;
		this.messages = new ArrayList<FlashMessage>();
	}
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
		try {
			this.controller.processDecode(context, component);
		} catch (NoPermissionException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			this.messages.add(new FlashMessage("You don't have a permisson.", FlashMessageType.WARNING, Boolean.FALSE));
		} catch (BreakBusinessRuleException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			this.messages.add(new FlashMessage("Article can't has empty title.", FlashMessageType.ERROR, Boolean.FALSE));
		} catch (ArticleExistsException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			this.messages.add(new FlashMessage("Article with this title already exists.", FlashMessageType.WARNING, Boolean.FALSE));
		} catch (ModelException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			this.messages.add(new FlashMessage("Service is unavailable, sorry.", FlashMessageType.ERROR, Boolean.FALSE));
		}
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) 
		throws IOException {
		JWLEncoder encoder = null;
		try {
			if (!this.messages.isEmpty()) {
				encoder = new EncodeError();
				for (FlashMessage flashMessage : this.messages) {
					encoder.addFlashMessage(flashMessage);
				}
			} else {
				this.controller.processRequest(context, component);
				encoder = this.controller.getResponseEncoder(component);
				encoder.encode();
			}
		} catch (NoPermissionException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			if (encoder == null) {
				encoder = new EncodeError();
			}
			encoder.addFlashMessage("You don't have a permisson.", FlashMessageType.WARNING, Boolean.FALSE);
		} catch (ModelException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			if (encoder == null) {
				encoder = new EncodeError();
			}
			encoder.addImplicitErrorFlashMessage();
		} catch (NullPointerException e) {
			Logger.getLogger(JWLCoreRenderer.class.getName()).log(Level.SEVERE, null, e);
			if (encoder == null) {
				encoder = new EncodeError();
			}
			encoder.addImplicitErrorFlashMessage();
//		} finally {
//			encoder.encodeCriticalFlashMessages();
			this.messages.clear();
		}
	}
}