package com.jwl.presentation.component.renderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.administration.renderer.EncodeListing;

public class EncodeTopicList extends AbstractEncodeTopicList{

	public EncodeTopicList(ArticleId id) {
		super(id);
	}

	@Override
	public void encodeResponse() {
		try {
			super.encodeFlashMessages();
			IPaginator<TopicTO> paginator= super.facade.getArticleForumTopics(articleId);
			super.encodeForm(paginator, getHeaderNames());
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
