package com.jwl.presentation.components.article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlOutputText;
import javax.naming.NoPermissionException;

import com.jwl.business.IPaginator;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.presentation.components.core.AbstractPresenter;
import com.jwl.presentation.enumerations.JWLElements;
import com.jwl.presentation.renderers.EncodeAdministrationConsole;
import com.jwl.presentation.renderers.EncodeAttach;
import com.jwl.presentation.renderers.EncodeCreate;
import com.jwl.presentation.renderers.EncodeEdit;
import com.jwl.presentation.renderers.EncodeHistoryListing;
import com.jwl.presentation.renderers.EncodeHistoryView;
import com.jwl.presentation.renderers.EncodeListing;
import com.jwl.presentation.renderers.EncodeNotExist;
import com.jwl.presentation.renderers.EncodeTopicCreate;
import com.jwl.presentation.renderers.EncodeTopicList;
import com.jwl.presentation.renderers.EncodeTopicView;
import com.jwl.presentation.renderers.EncodeView;
import com.jwl.presentation.renderers.units.FlashMessage;
import com.jwl.presentation.url.RequestMapDecoder;

public class ArticlePresenter extends AbstractPresenter {

	@Override
	public void renderDefault() {
		String articleTitle = getArticleTitle();
		if (articleTitle != null) {
			container.addAll(new EncodeNotExist().getEncodedComponent());
		} else {
			IPaginator<ArticleTO> paginator = this.getFacade().getPaginator();
			container.addAll(new EncodeListing(paginator).getEncodedComponent());
		}
	}
	
	public void renderCreate() {
		container.addAll(new EncodeCreate().getEncodedComponent());
	}
	
	public void renderEdit() {
		container.addAll(new EncodeEdit(this.getArticle()).getEncodedComponent());
	}
	
	public void renderList() {
		IPaginator<ArticleTO> paginator = this.getFacade().getPaginator();
		container.addAll(new EncodeListing(paginator).getEncodedComponent());
	}
	
	public void renderView() throws ModelException {
		this.renderView(getArticle());
	}
	
	public void renderView(ArticleTO article) throws ModelException {
//		List<ArticleTO> similarArticles = getFacade().getSimilarArticlesInView(article);		
		container.addAll(new EncodeView(article).getEncodedComponent());
	}

	public void renderAttachFile() {
		ArticleTO article = getArticle();
		container.addAll(new EncodeAttach(article).getEncodedComponent());
	}
	
	public void renderDownloadFile() throws IOException {
		HtmlOutputText output = new HtmlOutputText();
		output.setValue("Not yet implement.");
		output.encodeAll(this.context);
		container.add(output);
	}
	
	public void renderAdministrationConsole() {
		container.addAll(new EncodeAdministrationConsole().getEncodedComponent());
	}

	public void renderHistoryView() throws ModelException {
		HistoryTO history = super.getFacade().getHistory(this.getHistoryId());
		container.addAll(new EncodeHistoryView(this.getArticle(), history).getEncodedComponent());
	}

	public void renderHistoryList() throws ModelException {
		ArticleTO article = this.getArticle();
		List<HistoryTO> histories = super.getFacade().getHistories(article.getId());
		container.addAll(new EncodeHistoryListing(article, histories).getEncodedComponent());
	}
	
	public void renderTopicList() throws ModelException {
		IPaginator<TopicTO> topics = this.getFacade().getArticleForumTopics(getArticle().getId());
		container.addAll(new EncodeTopicList(getArticle(), topics).getEncodedComponent());
	}
	
	public void renderTopicCreate() {
		container.addAll(new EncodeTopicCreate(getArticle()).getEncodedComponent());
	}
	
	public void renderTopicView() throws ModelException {
		Integer topicId = this.getTopicId();
		this.renderTopicView(topicId);
	}
	
	public void renderTopicView(Integer topicId) throws ModelException {
		TopicTO topic = this.getFacade().getTopic(topicId);
		ArticleTO article = super.getArticle();
		boolean isAnswering = super.urlParser.containsAnswering();
		
		String stringQuopteTopicId = super.urlParser.getQuopteTopicId();
		Integer quopteTopicId = null;
		if (stringQuopteTopicId != null) {
			quopteTopicId = Integer.parseInt(stringQuopteTopicId);
		}
		
		container.addAll(new EncodeTopicView(topic, article, isAnswering, quopteTopicId).getEncodedComponent());
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
		
		Integer topicId = this.getFacade().createForumTopic(topic, getArticleId());
		renderTopicView(topicId);
	}
	
	public void decodePostReplyRequest() throws ModelException {
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
		renderView(article);
	}
	
	public void decodeArticleUpdate() throws NoPermissionException, ModelException {
		
		ArticleTO article = getFilledArticle();
		
		ArticleId articleId = getArticleIdFromFacade(article.getTitle());
		article.setId(articleId);
		
		this.getFacade().updateArticle(article);
		
		messages.add(new FlashMessage("Article was saved."));
		renderView(article);
	}
	
	public void decodeArticleDelete() throws ModelException {
		this.getFacade().deleteArticle(getArticleId());
		messages.add(new FlashMessage("Article was deleted."));
		renderList();
	}
	
	public void decodeArticleLock() throws ModelException {
		this.getFacade().lockArticle(getArticleId());
		messages.add(new FlashMessage("Article was locked."));
		renderList();
	}
	
	public void decodeArticleUnlock() throws ModelException {
		this.getFacade().unlockArticle(getArticleId());
		messages.add(new FlashMessage("Article was locked."));
		renderList();
	}
	
	public void decodeHistoryRestore() throws ModelException {
		this.getFacade().restoreArticle(getHistoryId());
		messages.add(new FlashMessage("Article was restored."));
		renderView();
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
		article.setEditor(getEditor());
		
		return article;
	}
	

	
}
