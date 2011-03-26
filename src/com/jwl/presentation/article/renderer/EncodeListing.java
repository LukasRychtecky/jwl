package com.jwl.presentation.article.renderer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jwl.business.IFacade;
import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleTO;
import com.jwl.presentation.article.enumerations.ListColumns;
import com.jwl.presentation.component.renderer.AbstractEncodeListing;

public class EncodeListing extends AbstractEncodeListing {

	public EncodeListing(IFacade facade) {
		super(facade);
	}

	@Override
	public void encodeResponse() {
		try {
			super.encodeFlashMessages();
			IPaginator<ArticleTO> paginator = this.facade.getPaginator();
			super.encodeLinkToCreateNewArticle();
			super.encodeListing(paginator, super.getHeaderNames(), this.getOrderableColumns());
		} catch (IOException ex) {
			Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE, null, ex);
			super.addImplicitErrorFlashMessage();
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeListing.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private Map<Integer, String> getOrderableColumns(){
		Map<Integer,String> oc = new HashMap<Integer, String>();
		oc.put(1, ListColumns.TITLE);
		oc.put(3, ListColumns.EDITOR);
		oc.put(5, ListColumns.CREATED);
		return oc;
	}
	
}
