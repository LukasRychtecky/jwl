package com.jwl.presentation.administration.renderer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jwl.business.IFacade;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.component.renderer.AbstractEncodeTopicView;
import com.jwl.presentation.component.renderer.EncodeView;

public class EncodeTopicView extends AbstractEncodeTopicView {

	protected Integer topicId;
	protected ArticleId articleId;
	protected boolean answering;
	protected Integer quotePostId;

	public EncodeTopicView(IFacade facade, Integer topicId,
			ArticleId articleId, boolean answering, Integer quotePostId) {
		super(facade);
		this.topicId = topicId;
		this.articleId = articleId;
		this.answering = answering;
		this.quotePostId = quotePostId;
	}

	@Override
	protected void encodeResponse() {
		try {
			super.encodeFlashMessages();
			TopicTO topic = this.facade.getTopic(topicId);
			super.encodeTopicPanel(topic);
			super.encodeTopicPanelActionButtons(topic, articleId);
			List<PostTO> replies = topic.getPosts();
			PostTO initialPost = replies.get(0);
			replies.remove(0);
			super.encodeReplyViewPanel(replies, true, topic.isClosed());
			replies.add(initialPost);
			if (answering) {
				PostTO quotedPost = null;
				for(PostTO post:replies){
					if(post.getId()==quotePostId.intValue()){
						quotedPost = post;
					}
				}
				super.encodeReplyForm(topic,quotedPost);
			}
		} catch (IOException e) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE,
					null, e);
			super.addImplicitErrorFlashMessage();
		} catch (ModelException e) {
			Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE,
					null, e);
			super.addImplicitErrorFlashMessage();
		} finally {
			try {
				super.encodeCriticalFlashMessages();
			} catch (IOException ex) {
				Logger.getLogger(EncodeView.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

}
