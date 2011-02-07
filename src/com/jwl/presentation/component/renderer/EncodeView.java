package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.exceptions.ModelException;

public class EncodeView extends AbstractEncodeView {

	protected ArticleId id;
	
	public EncodeView(IFacade facade, ArticleId id) {
		super(facade);
		this.id = id;
	}

	@Override
	public void encodeResponse() {
		try {
			super.encodeFlashMessages();
			ArticleTO article = this.facade.getArticle(this.id);
			super.encondeArticle(article);
			super.encodeCommonLinks(article);
		} catch (IOException e) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE, null, e);
			super.addImplicitErrorFlashMessage();
		} catch (ModelException e) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE, null, e);
			super.addImplicitErrorFlashMessage();
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
