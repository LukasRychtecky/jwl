package com.jwl.presentation.component.renderer;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.administration.renderer.EncodeListing;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Rychtecky
 */
public class EncodeHistoryListing extends AbstractEncodeHistoryListing {

	private ArticleId id;

	public EncodeHistoryListing(IFacade facade, ArticleId id) {
		super(facade);
		this.id = id;
	}


	@Override
	protected void encodeResponse() {
		try {
			super.encodeFlashMessages();
			List<HistoryTO> histories = super.facade.getHistories(this.id);
			if (!histories.isEmpty()) {
				super.encodeListing(histories, super.getHeaderNames());
			} else {
				super.addFlashMessage("No history found.");
				super.encodeFlashMessages();
			}
			super.encodeLinkToArticleListing();
		} catch (IOException ex) {
			super.addImplicitErrorFlashMessage();
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ModelException e) {
			super.addImplicitErrorFlashMessage();
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeHistoryListing.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
