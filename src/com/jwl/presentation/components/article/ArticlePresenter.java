package com.jwl.presentation.components.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.naming.NoPermissionException;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.core.AbstractComponent;
import com.jwl.presentation.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLContextKey;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.renderers.EncodeAdministrationConsole;
import com.jwl.presentation.renderers.EncodeAttach;
import com.jwl.presentation.renderers.EncodeCreate;
import com.jwl.presentation.renderers.EncodeEdit;
import com.jwl.presentation.renderers.EncodeHistoryListing;
import com.jwl.presentation.renderers.EncodeHistoryView;
import com.jwl.presentation.renderers.EncodeListing;
import com.jwl.presentation.renderers.EncodeTopicCreate;
import com.jwl.presentation.renderers.EncodeTopicList;
import com.jwl.presentation.renderers.EncodeTopicView;
import com.jwl.presentation.renderers.EncodeView;
import com.jwl.presentation.renderers.units.FlashMessage;
import com.jwl.presentation.url.RequestMapDecoder;

public class ArticlePresenter extends AbstractPresenter {

	@Override
	public void renderDefault() {
		IPaginator<ArticleTO> paginator = this.getFacade().getPaginator();
		container.addAll(new EncodeListing(paginator).getEncodedComponent());
	}
	
	public void renderCreate() {
		container.addAll(new EncodeCreate().getEncodedComponent());
	}
	
	public void renderEdit() {
		container.addAll(new EncodeEdit().getEncodedComponent());
	}
	
	public void renderList() {
		IPaginator<ArticleTO> paginator = this.getFacade().getPaginator();
		container.addAll(new EncodeListing(paginator).getEncodedComponent());
	}
	
	public void renderView() throws ModelException {
		ArticleTO article = (ArticleTO) super.context.getAttributes().get(JWLContextKey.ARTICLE);
		List<ArticleTO> similarArticles = getFacade().getSimilarArticlesInView(article);		
		container.addAll(new EncodeView(similarArticles).getEncodedComponent());
	}

	public void renderAttachFile() {
		container.addAll(new EncodeAttach().getEncodedComponent());
	}

	public void renderAdministrationConsole() {
		container.addAll(new EncodeAdministrationConsole().getEncodedComponent());
	}

	public void renderHistoryView() throws ModelException {
		container.addAll(new EncodeHistoryView().getEncodedComponent());
	}

	public void renderHistoryList() throws ModelException {
		container.addAll(new EncodeHistoryListing().getEncodedComponent());
	}
	
	public void renderTopicList() throws ModelException {
		container.addAll(new EncodeTopicList().getEncodedComponent());
	}
	
	public void renderTopicCreate() {
		container.addAll(new EncodeTopicCreate().getEncodedComponent());
	}
	
	public void renderTopicView() throws ModelException {
		boolean isAnswering = super.urlParser.containsAnswering();
		String stringQuopteTopicId = super.urlParser.getQuopteTopicId();
		Integer quopteTopicId = null;
		if (stringQuopteTopicId != null) {
			quopteTopicId = Integer.parseInt(stringQuopteTopicId);
		}
		
		container.addAll(new EncodeTopicView(isAnswering, quopteTopicId).getEncodedComponent());
	}
	
	public void decodeTopicCreate() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.FORUM_CREATE_TOPIC_FORM);
		
		PostTO post = new PostTO();
		post.setText(decoder.getValue(JWLElements.FORUM_TOPIC_TEXT));		
		List<PostTO> posts =new ArrayList<PostTO>();
		posts.add(post);

		TopicTO topic = new TopicTO();
		String subject = decoder.getValue(JWLElements.FORUM_SUBJECT);
		topic.setTitle(subject);
		topic.setPosts(posts);
		
		ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
		Integer topicId = this.getFacade().createForumTopic(topic, articleId);

		super.context.getAttributes().put(JWLContextKey.TOPIC_ID, topicId);
	}
	
	public void decodePostReply() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.FORUM_POST_REPLY_FORM);
		String text = decoder.getValue(JWLElements.FORUM_POST_TEXT);
		String id = decoder.getValue(JWLElements.FORUM_POST_TOPIC_ID);
		
		PostTO post = new PostTO();
		post.setText(text);
		Integer postId = new Integer(id);
		this.getFacade().addForumPost(post, postId);
	}
	
	public void decodeArticleCreate() throws NoPermissionException, ModelException {
		
		ArticleTO article = getFilledArticle();
		ArticleId articleId = this.getFacade().createArticle(article);
		article.setId(articleId);
		
		messages.add(new FlashMessage("Article was saved."));
		
		super.context.getAttributes().put(JWLContextKey.ARTICLE, article);
	}
	
	public void decodeArticleUpdate() throws NoPermissionException, ModelException {
		ArticleTO article = getFilledArticle();
		article.setId(this.getFacade().findArticleByTitle(article.getTitle()).getId());
		
		this.getFacade().updateArticle(article);
		
		messages.add(new FlashMessage("Article was saved."));
		
		super.context.getAttributes().put(JWLContextKey.ARTICLE, article);
	}
	
	public void decodeArticleDelete() throws ModelException {
		ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
		this.getFacade().deleteArticle(articleId);
		messages.add(new FlashMessage("Article was deleted."));
	}
	
	public void decodeArticleLock() throws ModelException {
		ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
		this.getFacade().lockArticle(articleId);
		messages.add(new FlashMessage("Article was locked."));
	}
	
	public void decodeArticleUnlock() throws ModelException {
		ArticleId articleId = (ArticleId) super.context.getAttributes().get(JWLContextKey.ARTICLE_ID);
		this.getFacade().unlockArticle(articleId);
		messages.add(new FlashMessage("Article was locked."));
	}
	
	public void decodeHistoryRestore() throws ModelException {
		HistoryId historyId = (HistoryId) super.context.getAttributes().get(JWLContextKey.HISTORY_ID);
		this.getFacade().restoreArticle(historyId);
		messages.add(new FlashMessage("Article was restored."));
	}
	
	public void decodeTopicList() throws ModelException {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.FORUM_TOPIC_ADMIN_FORM);
		
		List<Integer> topicIds = getCheckedTopicIds();
		if(topicIds.isEmpty()){
			return;
		}
		
		if (decoder.containsKey(JWLElements.FORUM_TOPIC_DELETE)) {
			this.getFacade().deleteForumTopics(topicIds);
		} else if (decoder.containsKey(JWLElements.FORUM_TOPIC_CLOSE)) {
			this.getFacade().closeForumTopics(topicIds);
		} else if (decoder.containsKey(JWLElements.FORUM_TOPIC_OPEN)) {
			this.getFacade().openForumTopics(topicIds);
		}
	}
	
	private List<Integer> getCheckedTopicIds() {
		int elementCheckBoxLength = JWLElements.FORUM_TOPIC_ADMIN_FORM.id.length()
			+ JWLElements.FORUM_TOPIC_CHBX.id.length()
			+ (2 * AbstractComponent.JWL_HTML_ID_SEPARATOR.length());
		
		List<Integer> topicIds = new ArrayList<Integer>();
		for (Entry<String, String> e : getRequestParamMap().entrySet()) {
			if (e.getKey().contains(JWLElements.FORUM_TOPIC_CHBX.id)) {
				String idPart = e.getKey().substring(elementCheckBoxLength);
				int topicId = Integer.parseInt(idPart);;
				topicIds.add(topicId);
			}
		}
		return topicIds;
	}
	
	private ArticleTO getFilledArticle() {
		RequestMapDecoder decoder = getRequestMapDecoder(JWLElements.EDIT_FORM);
		
		ArticleTO article = new ArticleTO();
		article.setTitle(decoder.getValue(JWLElements.EDIT_TITLE));
		article.setText(decoder.getValue(JWLElements.EDIT_TEXT));

		String[] tags = decoder.getValue(JWLElements.EDIT_TAGS).split(",");
		for (int i = 0; i < tags.length; i++) {
			article.addTag(tags[i]);
		}

		article.setChangeNote(decoder.getValue(JWLElements.EDIT_CHANGE_NOTE));
		
		String editor = getFacade().getIdentity().getUserName();
		if (editor.isEmpty()) {
			editor = (String) this.context.getAttributes().get(JWLContextKey.USER_IP);
		}
		article.setEditor(editor);
		
		return article;
	}
	

	
}
