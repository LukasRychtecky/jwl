package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;

/**
 *
 * @author Lukas Rychtecky
 */
public class EncodeHistoryView extends AbstractEncodeHistoryView {
	private HistoryId id;

	public EncodeHistoryView(HistoryId id) {
		this.id = id;
	}

	@Override
	public void encodeResponse() {
		HistoryTO history = null;
		try {
			super.encodeFlashMessages();
			history = super.facade.getHistory(this.id);

			if (history == null) {
				super.addFlashMessage("History not found.", FlashMessageType.WARNING, Boolean.FALSE);
			} else {
				super.enconde(history);
			}
		} catch (IOException e) {
			Logger.getLogger(EncodeHistoryView.class.getName()).log(Level.SEVERE, null, e);
			super.addImplicitErrorFlashMessage();
		} catch (ModelException e) {
			super.addImplicitErrorFlashMessage();
			Logger.getLogger(EncodeHistoryView.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeHistoryView.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
